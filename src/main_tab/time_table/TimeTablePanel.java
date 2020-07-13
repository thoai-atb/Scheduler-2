package main_tab.time_table;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import main_tab.MainPanel;
import main_tab.subject.IDHiddenTableModel;

public class TimeTablePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private MainPanel parent;
	private JButton showB;
	private JButton deleteB;
	private IDHiddenTableModel tableModel = new IDHiddenTableModel(new String[] {"Name"});
	private JTable table;

	public TimeTablePanel(MainPanel parent) {
		this.parent = parent;
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("TIMETABLE"));
		
		table = new JTable(tableModel);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel control = new JPanel();
		this.add(control, BorderLayout.SOUTH);
		control.add(showB = new JButton("Show"));
		control.add(deleteB = new JButton("Delete"));
	}
}
