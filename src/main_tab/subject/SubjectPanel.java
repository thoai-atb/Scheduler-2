package main_tab.subject;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main_tab.MainPanel;

public class SubjectPanel extends JPanel implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private IDHiddenTableModel tableModel = new IDHiddenTableModel(new String[] {"Subject"});
	private JButton addB, deleteB;
	private MainPanel parent;
	
	public SubjectPanel(MainPanel parent) {
		this.parent = parent;
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("SUBJECT"));
		table = new JTable(tableModel);
		table.getSelectionModel().addListSelectionListener(this);
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
		String sql = "SELECT * FROM subject";
		Statement st = parent.getConnection().createStatement();
		ResultSet result = st.executeQuery(sql);
		tableModel.clear();
		while(result.next()) {
			tableModel.addRecord(new String[] {result.getString(1), result.getString(2)});
		}
		st.close();
		parent.updateClassPanel();
	}
	
	private void add() throws SQLException {
		String name = JOptionPane.showInputDialog("Enter Subject's Name");
		if(name == null)
			return;
		String sql = String.format("INSERT INTO subject (name) VALUES ('%s');", name);
		Statement st = parent.getConnection().createStatement();
		st.executeUpdate(sql);
		loadTable();
	}
	
	private void delete() throws SQLException {
		int[] indices = table.getSelectedRows();
		if(indices.length == 0) {
			JOptionPane.showMessageDialog(null, "Please choose a subject to be deleted");
			return;
		}
		for(int i = 0; i<indices.length; i++) {
			int index = indices[i];
			String id = tableModel.getID(index);
			String sql = String.format("DELETE FROM subject WHERE id = '%s';", id);
			Statement st = parent.getConnection().createStatement();
			st.executeUpdate(sql);
		}
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
	
	public String getSelectedSubject() {
		int index = table.getSelectedRow();
		if(index < 0)
			return null;
		return tableModel.getID(index);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		parent.updateClassPanel();
	}


}
