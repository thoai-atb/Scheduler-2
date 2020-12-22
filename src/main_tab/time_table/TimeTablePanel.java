package main_tab.time_table;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import main_tab.ClassCourse;
import main_tab.MainPanel;

public class TimeTablePanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private MainPanel parent;
	private JButton showB, deleteB, renameB;
	private IDHiddenTableModel tableModel = new IDHiddenTableModel(new String[] {"Name"});
	private JTable table;

	public TimeTablePanel(MainPanel parent) {
		this.parent = parent;
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("TIMETABLE"));
		
		table = new JTable(tableModel);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel control = new JPanel();
		this.add(control, BorderLayout.SOUTH);
		control.add(showB = new JButton("Show"));
		showB.addActionListener(this);
		control.add(deleteB = new JButton("Delete"));
		deleteB.addActionListener(this);
		control.add(renameB = new JButton("Rename"));
		renameB.addActionListener(this);
		
		try {
			loadTable();
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}
	
	public void loadTable() throws SQLException {
		String sql = "SELECT * FROM timetable;";
		Statement st = parent.getConnection(false).createStatement();
		ResultSet r = st.executeQuery(sql);
		tableModel.clear();
		while(r.next()) {
			String name = r.getString("name");
			String id = r.getString("id");
			tableModel.addRecord(new String[] {id, name});
		}
	}
	
	public void saveTimeTable(TimeTable table, String name) throws SQLException {
		PreparedStatement st = parent.getConnection(true).prepareStatement("INSERT INTO timetable (name) VALUES (?)");
		st.setString(1, name);
		st.executeUpdate();

		String sql = "SELECT last_insert_rowid()";
		Statement s = parent.getConnection(false).createStatement();
		ResultSet idR = s.executeQuery(sql);
		if(!idR.next())
			return;
		String tid = idR.getString(1);
		for(ClassCourse c : table.classes) {
			PreparedStatement st2 = parent.getConnection(true).prepareStatement("INSERT INTO schedule (class_id, timetable_id) VALUES (?, ?)");
			st2.setString(1, c.id);
			st2.setString(2, tid);
			st2.executeUpdate();
		}
		
		loadTable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		try {
			if(src == showB) {
				int index = table.getSelectedRow();
				if(index < 0) {
					JOptionPane.showMessageDialog(null, "Please choose a timetable");
					return;
				}
				TimeTableGUI.createView(parent.getTimeTable(tableModel.getID(index)));
			} else if(src == deleteB) {
				int indices[] = table.getSelectedRows();
				if(indices.length == 0) {
					JOptionPane.showMessageDialog(null, "Please choose a timetable");
					return;
				}
				for(int index : indices) {
					String sql = String.format("DELETE FROM timetable WHERE id = '%s'", tableModel.getID(index));
					Statement st = parent.getConnection(true).createStatement();
					st.executeUpdate(sql);
				}
				loadTable();
			} else if(src == renameB) {
				int index = table.getSelectedRow();
				if(index < 0) {
					JOptionPane.showMessageDialog(null, "Please choose a timetable");
					return;
				}
				String name = JOptionPane.showInputDialog(null, "Enter new name:", tableModel.getValueAt(index, 0));
				if(name == null || name.isBlank()) {
					JOptionPane.showMessageDialog(null, "Name can't be blank!");
					return;
				}
				String sql = String.format("UPDATE timetable SET name = '%s' WHERE id = '%s';", name, tableModel.getID(index));
				Statement st = parent.getConnection(true).createStatement();
				st.executeUpdate(sql);
				loadTable();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
