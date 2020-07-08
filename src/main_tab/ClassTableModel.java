package main_tab;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ClassTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private int numSections;

	public ClassTableModel(int numSections) {
		this.numSections = numSections;
	}
	
	String[] colNames = new String[] {"#", "Day", "Start", "Duration"};
	List<ClassCourse> data = new ArrayList<ClassCourse>();
	
	public void clear() {
		data.clear();
		this.fireTableDataChanged();
	}
	
	public void addRecord(String classID, String[][] record) {
		data.add(record);
		this.fireTableDataChanged();
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
		return data.get(rowIndex)[columnIndex + 1];
	}
	
	public String getID(int rowIndex) {
		return data.get(rowIndex)[0];
	}
}

class ClassCourse { // Can't name it "Class"
	String id;
	List<Section> sections = new ArrayList<Section>();
	
	public ClassCourse(String id) {
		this.id = id;
	}
	
	public void addSection(String day, String start, String duration) {
		sections.add(new Section(day, start, duration));
	}
}

class Section {
	String day, start, duration;
	public Section(String day, String start, String duration) {
		this.day = day;
		this.start = start;
		this.duration = duration;
	}
}
