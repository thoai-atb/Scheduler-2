
package main_tab.time_table;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main_tab.MainPanel;

public class TimeTableGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private JTable jTable;
	private JButton nextButton, previousButton, saveButton, closeButton;
	private JLabel indexLabel;
	private int currentIndex = 0;
	private List<TimeTable> tables;
	private MainPanel panel;
	
	public static void createView(TimeTable table) {
		List<TimeTable> list = new ArrayList<TimeTable>();
		list.add(table);
		new TimeTableGUI(list, null, true);
	}
	
	public TimeTableGUI(List<TimeTable> tables, MainPanel panel, boolean readOnly) {
		super("Time Table");
		setSize(600, 400);
		setVisible(true);
		setResizable(false);
		
		this.tables = tables;
		this.panel = panel;

		String[] col = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		tableModel = new DefaultTableModel(col, 0);
		jTable = new JTable(tableModel);
		jTable.setEnabled(false);

		setLayout(new BorderLayout());
		add(new JScrollPane(jTable), BorderLayout.CENTER);
		
		JPanel controller = new JPanel();
		if(!readOnly)
			controller.setLayout(new GridLayout(0, 5, 10, 10));
		
		previousButton = new JButton("<");
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentIndex --;
				if(currentIndex < 0)
					currentIndex = 0;
				loadTable();
			} 
		});
		nextButton = new JButton(">");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentIndex ++;
				if(currentIndex >= tables.size())
					currentIndex = tables.size() - 1;
				loadTable();
			} 
		});
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			} 
		});
		closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			} 
		});
		
		indexLabel = new JLabel("", JLabel.CENTER);
		controller.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		if(!readOnly) {
			controller.add(saveButton);
			controller.add(previousButton);
			controller.add(indexLabel);
			controller.add(nextButton);
		}
		controller.add(closeButton);
		add(controller, BorderLayout.SOUTH);
		
		
		
		loadTable();
	}
	
	private void save() {
		String name = JOptionPane.showInputDialog("Save as:");
		if(name == null)
			return;
		try {
			panel.saveTimeTable(tables.get(currentIndex), name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadTable() {
		tableModel.setRowCount(0);
		if(tables == null || tables.size() == 0)
			return;
		String[][] table = tables.get(currentIndex).getTable();
		for(int i = 0; i<table.length; i++) {
			Object[] objs = (Object[]) table[i];
			tableModel.addRow(objs);
		}
		
		indexLabel.setText(currentIndex+1 + "/" + tables.size());
	}
	
}
