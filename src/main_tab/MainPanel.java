package main_tab;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JPanel;

import main_tab.class_course.ClassPanel;

public class MainPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Connection con;
	private SubjectPanel subjectPanel;
	private ClassPanel classPanel;
	
	public MainPanel(Connection con) {
		this.con = con;
		this.setLayout(new GridLayout());
		this.add(subjectPanel = new SubjectPanel(this));
		this.add(classPanel = new ClassPanel(this));
	}
	
	public Connection getConnection() {
		return con;
	}
	
	public String getSelectedSubject() {
		return subjectPanel.getSelectedSubject();
	}
	
	public void updateClassPanel() {
		try {
			if(classPanel != null)
				classPanel.loadTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
