package sql_tab;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ResultTableModel extends AbstractTableModel {
	String[] colNames;
	List<String[]> data = new ArrayList<String[]>();
	
	public ResultTableModel(ResultSet resultSet) {
		try {
			ResultSetMetaData m = resultSet.getMetaData();
			int n = m.getColumnCount();
			colNames = new String[n];
			for(int i = 0; i < n; i++)
				colNames[i] = m.getColumnName(i + 1);
			
			while(resultSet.next()) {
				String[] record = new String[n];
				for(int i = 0; i<n; i++)
					record[i] = resultSet.getString(i + 1);
				data.add(record);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex)[columnIndex];
	}

}
