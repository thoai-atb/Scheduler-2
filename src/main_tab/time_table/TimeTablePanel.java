package main_tab.time_table;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main_tab.MainPanel;

public class TimeTablePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private MainPanel parent;
	
	public TimeTablePanel (MainPanel parent) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("TIMETABLE"));
		this.parent = parent;
	}

}
