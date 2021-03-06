package main_tab.class_course;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import main_tab.ClassCourse;

public class ClassTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	public ClassTableModel() {
	}
	
	String[] colNames = new String[] {"#", "Day", "Start", "Duration"};
	List<ClassCourse> data = new ArrayList<ClassCourse>();
	
	public void clear() {
		data.clear();
		this.fireTableDataChanged();
	}
	
	public void addRecord(String classID, List<String[]> records) {
		ClassCourse c = new ClassCourse(classID, null);
		for(String[] r : records) {
			c.addSection(r[0], r[1], r[2]);
		}
		data.add(c);
//		System.out.println();
//		for(ClassCourse s : data)
//			System.out.println(s);
		this.fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	@Override
	public int getRowCount() {
		int count = 0;
		for(ClassCourse c : data)
			count += c.sectionCount();
		return count;
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}
	
	private int[] getDataIndex(int rowIndex) {
		int i = 0;
		int t = rowIndex + 1;
		for(ClassCourse c : data) {
			t -= c.sections.size();
			if(t <= 0)
				return new int[] {i, t + c.sections.size() - 1};
			i ++;
		}
		return new int[] {-1, -1};
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int[] indices = getDataIndex(rowIndex);
		
		if(columnIndex == 0)
			return indices[1] == 0? indices[0] : "";
		
		if(columnIndex == 1)
			return threeLetterDay(data.get(indices[0]).sections.get(indices[1]).day);
		if(columnIndex == 2)
			return data.get(indices[0]).sections.get(indices[1]).start;
		if(columnIndex == 3)
			return data.get(indices[0]).sections.get(indices[1]).duration;
		
		return null;
	}
	
	private String threeLetterDay(String day) {
		return day.substring(0, 3);
	}
	
	public String getID(int rowIndex) {
		return data.get(getDataIndex(rowIndex)[0]).id;
	}
}