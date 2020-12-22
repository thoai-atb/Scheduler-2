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
	private SubjectTableModel tableModel;
	private JButton addB, deleteB, renameB;
	private MainPanel parent;
	
	public SubjectPanel(MainPanel parent) {
		this.parent = parent;
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("SUBJECT"));
		table = new JTable();
		tableModel = new SubjectTableModel(table, parent);
		table.getSelectionModel().addListSelectionListener(this);
//		table.setDefaultRenderer(Boolean.class, new SubjectTableRenderer());
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel control = new JPanel();
		this.add(control, BorderLayout.SOUTH);
		addB = new JButton("Add");
		control.add(addB);
		addB.addActionListener(this);
		deleteB = new JButton("Delete");
		control.add(deleteB);
		deleteB.addActionListener(this);
		renameB = new JButton("Rename");
		control.add(renameB);
		renameB.addActionListener(this);
		
		try {
			loadTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadTable() throws SQLException {
		String sql = "SELECT * FROM subject";
		Statement st = parent.getConnection(false).createStatement();
		ResultSet result = st.executeQuery(sql);
		tableModel.clear();
		while(result.next()) {
			tableModel.addRecord(result.getString("id"), result.getString("name"), result.getBoolean("included"), result.getBoolean("required"));
		}
		st.close();
		parent.updateClassPanel();
	}
	
	private void add() throws SQLException {
		String name = JOptionPane.showInputDialog("Enter Subject's Name");
		if(name == null)
			return;
		String sql = String.format("INSERT INTO subject (name) VALUES ('%s');", name);
		Statement st = parent.getConnection(true).createStatement();
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
			Statement st = parent.getConnection(true).createStatement();
			st.executeUpdate(sql);
		}
		loadTable();
	}
	
	private void rename() throws SQLException {
		int index = table.getSelectedRow();
		if(index < 0) {
			JOptionPane.showMessageDialog(null, "Please choose a subject to be renamed");
			return;
		}
		String name = JOptionPane.showInputDialog(null, "Please enter a new name for this subject", tableModel.getValueAt(index, 0));
		if(name == null || name.isBlank()) {
			JOptionPane.showMessageDialog(null, "Name can't be blank");
			return;
		}
		String sql = String.format("UPDATE subject SET name = '%s' WHERE id = '%s';", name, tableModel.getID(index));
		Statement st = parent.getConnection(true).createStatement();
		st.executeUpdate(sql);
		loadTable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource() == addB) {
				add();
			} else if(e.getSource() == deleteB) {
				delete();
			} else if(e.getSource() == renameB) {
				rename();
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
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
