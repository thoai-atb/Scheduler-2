package main_tab;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import main_tab.class_course.ClassPanel;
import main_tab.scheduler.Scheduler;
import main_tab.subject.SubjectPanel;
import main_tab.time_table.TimeTablePanel;

public class MainPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Connection con;
	private SubjectPanel subjectPanel;
	private ClassPanel classPanel;
	private TimeTablePanel schedulePanel;
	private TimeTablePanel timeTablePanel;
	private Scheduler scheduler;
	
	public MainPanel(Connection con) {
		this.con = con;
		this.setLayout(new GridLayout());
		this.add(subjectPanel = new SubjectPanel(this));
		this.add(classPanel = new ClassPanel(this));
		this.add(timeTablePanel = new TimeTablePanel(this));
		
		scheduler = new Scheduler(this);
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
	
	public void arrange() throws SQLException {
		scheduler.arrange();
	}

	public List<Subject> getData() throws SQLException {
		String sql = "SELECT * FROM subject;";
		Statement subjectS = con.createStatement();
		ResultSet subjectR = subjectS.executeQuery(sql);
		List<Subject> data = new ArrayList<Subject>();
		while(subjectR.next()) {
			Subject subject = new Subject(subjectR.getString("id"), subjectR.getString("name"));
			sql = "SELECT * FROM class C INNER JOIN subject S ON C.subject_id = S.id;";
			Statement classS = con.createStatement();
			ResultSet classR = classS.executeQuery(sql);
			while(classR.next()) {
				ClassCourse c = new ClassCourse(classR.getString("id"));
				sql = "SELECT * FROM section S INNER JOIN class C ON S.class_id = C.id";
			}
			
			
		}
		return null;
	}

}
