package main_tab.time_table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main_tab.ClassCourse;
import main_tab.Section;

public class TimeTable implements Comparable<TimeTable> {
	
	List<ClassCourse> classes = new ArrayList<ClassCourse>();
	
	public boolean addClass(ClassCourse c) {
		for(ClassCourse c1 : classes)
			if(conflict(c, c1))
				return false;
		classes.add(c);
		return true;
	}
	
	public boolean conflict(ClassCourse c1, ClassCourse c2) {
		for(Section a : c1.sections)
			for(Section b : c2.sections) {
				if(conflict(a, b))
					return true;
			}
		return false;
	}
	
	public boolean conflict(Section a, Section b) {
		if(!a.day.equals(b.day)) {
			return false;
		}
		int as = Integer.parseInt(a.start);
		int bs = Integer.parseInt(b.start);
		int ad = Integer.parseInt(a.duration);
		int bd = Integer.parseInt(b.duration);

		boolean con = as < bs + bd && bs < as + ad;
		return con;
	}
	
	public String[][] getTable(){
		String[][] table = new String[16][7];

		HashMap<String, Integer> days = new HashMap<String, Integer>();
		days.put("Monday", 0);
		days.put("Tuesday", 1);
		days.put("Wednesday", 2);
		days.put("Thursday", 3);
		days.put("Friday", 4);
		days.put("Saturday", 5);
		days.put("Sunday", 6);

		for(ClassCourse c : classes) {
			for(Section section : c.sections) {
				int duration = Integer.parseInt(section.duration);
				int start = Integer.parseInt(section.start);
				for(int i = 0; i<duration; i++) {
					table[start-1 + i][days.get(section.day)] = c.getName(); 
				}
			}
		}
		
		return table;
	}

	@Override
	public int compareTo(TimeTable o) {
		return o.classes.size() - this.classes.size();
	}
}
