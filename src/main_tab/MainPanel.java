package main_tab;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import main_frame.MainFrame;
import main_tab.class_course.ClassPanel;
import main_tab.subject.SubjectPanel;
import main_tab.time_table.TimeTable;
import main_tab.time_table.TimeTablePanel;

public class MainPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private SubjectPanel subjectPanel;
	private ClassPanel classPanel;
	private TimeTablePanel timeTablePanel;
	private MainFrame frame;
	
	public MainPanel(MainFrame frame) {
		this.frame = frame;
		this.setLayout(new GridLayout());
		this.add(subjectPanel = new SubjectPanel(this));
		this.add(classPanel = new ClassPanel(this));
		this.add(timeTablePanel = new TimeTablePanel(this));
	}
	
	public Connection getConnection(boolean update) {
		return frame.getConnection(update);
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
	
	public void saveTimeTable(TimeTable table, String name) throws SQLException {
		timeTablePanel.saveTimeTable(table, name);
	}
	
	public TimeTable getTimeTable(String timetableID)  throws SQLException {
		String sql = String.format("SELECT * FROM class WHERE id IN (SELECT class_id FROM schedule WHERE timetable_id = '%s');", timetableID);
		Statement classS = getConnection(false).createStatement();
		ResultSet classR = classS.executeQuery(sql);
		TimeTable timetable = new TimeTable();
		while(classR.next()) {
			sql = String.format("SELECT * FROM subject WHERE id = '%s'", classR.getString("subject_id"));
			Statement subjectS = getConnection(false).createStatement();
			ResultSet subjectR = subjectS.executeQuery(sql);
			subjectR.next();
			Subject subject = new Subject(subjectR.getString("id"), subjectR.getString("name"), subjectR.getBoolean("included"), subjectR.getBoolean("required"));
			String classID = classR.getString("id");
			ClassCourse c = new ClassCourse(classID, subject);
			
			sql = String.format("SELECT * FROM section WHERE class_id = '%s'", c.id);
			Statement sectionS = getConnection(false).createStatement();
			ResultSet sectionR = sectionS.executeQuery(sql);
			while(sectionR.next()) {
				String day = sectionR.getString("day");
				String start = sectionR.getString("start");
				String duration = sectionR.getString("duration");
				c.addSection(day, start, duration);
			}
			timetable.addClass(c);
		}
		return timetable;
	}

	public List<Subject> getIncludedSubjects() throws SQLException {
		String sql = "SELECT * FROM subject WHERE included = 1;";
		Statement subjectS = getConnection(false).createStatement();
		ResultSet subjectR = subjectS.executeQuery(sql);
		List<Subject> data = new ArrayList<Subject>();
		while(subjectR.next()) {
			Subject subject = new Subject(subjectR.getString("id"), subjectR.getString("name"), subjectR.getBoolean("included"), subjectR.getBoolean("required"));
			sql = String.format("SELECT * FROM class WHERE subject_id = '%s'", subject.id);
			Statement classS = getConnection(false).createStatement();
			ResultSet classR = classS.executeQuery(sql);
			while(classR.next()) {
				ClassCourse c = new ClassCourse(classR.getString("id"), subject);
				sql = String.format("SELECT * FROM section WHERE class_id = '%s'", c.id);
				Statement sectionS = getConnection(false).createStatement();
				ResultSet sectionR = sectionS.executeQuery(sql);
				while(sectionR.next()) {
					String day = sectionR.getString("day");
					String start = sectionR.getString("start");
					String duration = sectionR.getString("duration");
					c.addSection(day, start, duration);
				}
				subject.addClass(c);
			}
			data.add(subject);
		}
		return data;
	}

}
