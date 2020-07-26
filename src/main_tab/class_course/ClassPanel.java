package main_tab.class_course;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import main_tab.MainPanel;

public class ClassPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private ClassTableModel tableModel = new ClassTableModel();
	private JButton addB, deleteB;
	private MainPanel parent;
	
	
	public ClassPanel(MainPanel parent) {
		this.parent = parent;
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("CLASS"));
		table = new JTable(tableModel);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel control = new JPanel();
		addB = new JButton("Add");
		control.add(addB);
		addB.addActionListener(this);
		deleteB = new JButton("Delete");
		control.add(deleteB);
		deleteB.addActionListener(this);
		this.add(control, BorderLayout.SOUTH);
		
		try {
			loadTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadTable() throws SQLException {
		tableModel.clear();
		String subjectID = parent.getSelectedSubject();
		if(subjectID == null)
			return;
		
		String sql = String.format("SELECT id FROM class WHERE subject_id = '%s'", subjectID);
		Statement st = parent.getConnection(false).createStatement();
		ResultSet result = st.executeQuery(sql);
		while(result.next()) {
			String classID = result.getString("id");
			sql = String.format("SELECT day, start, duration FROM section WHERE class_id = '%s'", classID);
			Statement st2 = parent.getConnection(false).createStatement();
			ResultSet sectionR = st2.executeQuery(sql);
			List<String[]> sections = new ArrayList<String[]>();
			while(sectionR.next()) {
				sections.add(new String[] {sectionR.getString("day"), sectionR.getString("start"), sectionR.getString("duration")});
			}
			tableModel.addRecord(classID, sections);
		}
		st.close();
	}
	
	private void add() throws SQLException {
		String subject = parent.getSelectedSubject();
		if(subject == null) {
			JOptionPane.showMessageDialog(null, "Please choose a subject to add the new class");
			return;
		}
		new CreateClassFrame(this, parent.getSelectedSubject());
	}
	
	private void delete() throws SQLException {
		if(table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "Please choose a class to delete");
			return;
		}
		String classID = tableModel.getID(table.getSelectedRow());
		String sql = String.format("DELETE FROM class WHERE id = '%s';", classID);
		Statement st = parent.getConnection(true).createStatement();
		st.executeUpdate(sql);
		loadTable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addB) {
			try {
				add();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(e.getSource() == deleteB) {
			try {
				delete();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void addNewClass(String subjectID, String[][] sections) throws SQLException {
		String sql = String.format("INSERT INTO class (subject_id) VALUES ('%s');", subjectID);
		Statement st = parent.getConnection(true).createStatement();
		st.executeUpdate(sql);
		
		sql = String.format("SELECT last_insert_rowid()");
		ResultSet classIDR = st.executeQuery(sql);
		if(!classIDR.next()) {
			System.out.println("Class ID can't be retrieved");
			return;
		}
		
		String classID = classIDR.getString(1);
		
		for(int i = 0; i<sections.length; i++) {
			String[] section = sections[i];
			sql = String.format("INSERT INTO section (class_id, day, start, duration) VALUES ('%s', '%s', '%s', '%s');", classID, section[0], section[1], section[2]);
			st.executeUpdate(sql);
		}
		
		loadTable();
	}
}
