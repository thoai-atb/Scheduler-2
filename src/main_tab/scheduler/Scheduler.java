package main_tab.scheduler;

import java.sql.SQLException;
import java.util.List;

import main_tab.MainPanel;
import main_tab.Subject;
import main_tab.time_table.TimeTableGUI;

public class Scheduler {
	
	private MainPanel panel;
	
	public Scheduler(MainPanel panel) {
		this.panel = panel;
	}
	
	public void arrange() throws SQLException {
		List<Subject> subjectList = panel.getData();
		panel.getConnection();
		new TimeTableGUI(null, panel);
	}

}
