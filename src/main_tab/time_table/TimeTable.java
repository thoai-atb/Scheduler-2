package main_tab.time_table;

import java.util.ArrayList;
import java.util.List;

import main_tab.ClassCourse;

public class TimeTable {
	
	List<ClassCourse> classes = new ArrayList<ClassCourse>();
	
	public void addClass(ClassCourse c) {
		classes.add(c);
	}
	public String[][] getTable(){
		return null;
	}
}
