package main_tab.schedule;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import main_tab.MainPanel;
import main_tab.subject.IDHiddenTableModel;

public class SchedulePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private MainPanel parent;
	private JButton scheduleB;
	private IDHiddenTableModel tableModel = new IDHiddenTableModel(new String[] {"Saved Timetable"});
	private JTable table;

	public SchedulePanel(MainPanel parent) {
		this.parent = parent;
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("SCHEDULE"));
		
		table = new JTable(tableModel);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel control = new JPanel();
		this.add(control, BorderLayout.SOUTH);
		control.add(scheduleB = new JButton("Schedule"));
	}
}
