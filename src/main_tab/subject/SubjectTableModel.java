package main_tab.subject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import main_tab.MainPanel;
import main_tab.time_table.IDHiddenTableModel;

public class SubjectTableModel extends IDHiddenTableModel{

	private static final long serialVersionUID = 1L;
	private List<Boolean> included, required;
	private MainPanel panel;

	public SubjectTableModel(JTable table, MainPanel panel) {
		super(new String[] {"Name", "Included", "Required"});
		included = new ArrayList<Boolean>();
		required = new ArrayList<Boolean>();
		this.panel = panel;
		table.setModel(this);
	}
	
	private int columnType(int columnIndex) {
		switch(getColumnName(columnIndex)) {
			case "Included": return 1;
			case "Required": return 2;
		}
		return 0;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		boolean n = (Boolean) aValue;
		
		try {
			if(columnType(columnIndex) == 1) {
				included.set(rowIndex, n);
				String sql = String.format("UPDATE subject SET included = ? WHERE id = ?;");
				PreparedStatement st = panel.getConnection(true).prepareStatement(sql);
				st.setBoolean(1, n);
				st.setString(2, this.getID(rowIndex));
				st.execute();
			}
			
			else if(columnType(columnIndex) == 2) {
				required.set(rowIndex, n);
				String sql = String.format("UPDATE subject SET required = ? WHERE id = ?;");
				PreparedStatement st = panel.getConnection(true).prepareStatement(sql);
				st.setBoolean(1, n);
				st.setString(2, this.getID(rowIndex));
				st.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnType(columnIndex) != 0;
    }
	
	@Override
	public void clear() {
		super.clear();
		included.clear();
		required.clear();
	}
	
	public void addRecord(String id, String name, boolean include, boolean require) {
		included.add(include);
		required.add(require);
		super.addRecord(new String[] {id, name});
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int type = columnType(columnIndex);
		if(type == 1)
			return included.get(rowIndex);
		if(type == 2)
			return required.get(rowIndex);
		return super.getValueAt(rowIndex, columnIndex);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(getColumnName(columnIndex) == "Included" || getColumnName(columnIndex) == "Required")
			return Boolean.class;
        return String.class;
    }

}
