package main_tab.scheduler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import main_tab.ClassCourse;
import main_tab.MainPanel;
import main_tab.Subject;
import main_tab.time_table.TimeTable;
import main_tab.time_table.TimeTableGUI;

public class Scheduler {
	
	private MainPanel panel;
	
	public Scheduler(MainPanel panel) {
		this.panel = panel;
	}
	
	public void arrange() throws SQLException {
		List<TimeTable> tables = new ArrayList<TimeTable>();
		
		List<Subject> subjectList = panel.getIncludedSubjects();
		if(subjectList.size() == 0) {
			JOptionPane.showMessageDialog(null, "There are no subjects included");
			return;
		}
		for(Subject s : subjectList) {
			if(s.count() == 0 && s.required) {
				JOptionPane.showMessageDialog(null, String.format("The subject %s has no available classes", s.name));
				return;
			}
		}
		
		int[] indices = new int[subjectList.size()];
		for(int i = 0; i<indices.length; i++) {
			Subject subject = subjectList.get(i);
			indices[i] = subject.required? 0 : -1;
		}
		
		boolean over = false;
		while(!over) {
			TimeTable timeTable = new TimeTable();
			boolean valid = true;
			for(int i = 0; i<subjectList.size(); i++) {
				if(indices[i] == -1)
					continue;
				Subject subject = subjectList.get(i);
				ClassCourse c = subject.getCourse(indices[i]);
				if(!timeTable.addClass(c)) {
					valid = false;
					break;
				}
			}
			
			if(valid)
				tables.add(timeTable);
			
			for(int i = 0; i<indices.length; i++) {
				indices[i] ++;
				if(indices[i] < subjectList.get(i).count())
					break;
				indices[i] = subjectList.get(i).required? 0 : -1;
				if(i == indices.length-1)
					over = true;
			}
		}
		
		tables.sort(null);
		new TimeTableGUI(tables, panel, false);
	}

}
