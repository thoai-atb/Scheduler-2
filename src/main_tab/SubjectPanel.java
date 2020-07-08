package main_tab;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SubjectPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private IDHiddenTableModel tableModel = new IDHiddenTableModel(new String[] {"Subject"});
	private JButton addB, deleteB;
	private Connection con;
	
	public SubjectPanel(Connection con) {
		this.con = con;
		
		this.setLayout(new BorderLayout());
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
		String sql = "SELECT * FROM subject";
		Statement st = con.createStatement();
		ResultSet result = st.executeQuery(sql);
		tableModel.clear();
		while(result.next()) {
			tableModel.addRecord(new String[] {result.getString(1), result.getString(2)});
		}
		st.close();
	}
	
	private void add() throws SQLException {
		String name = JOptionPane.showInputDialog("Enter Subject's Name");
		String sql = String.format("INSERT INTO subject (name) VALUES ('%s');", name);
		Statement st = con.createStatement();
		st.executeUpdate(sql);
		loadTable();
	}
	
	private void delete() throws SQLException {
		int[] indices = table.getSelectedRows();
		for(int i = 0; i<indices.length; i++) {
			int index = indices[i];
			String id = tableModel.getID(index);
			String sql = String.format("DELETE FROM subject WHERE id = '%s';", id);
			Statement st = con.createStatement();
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


}
