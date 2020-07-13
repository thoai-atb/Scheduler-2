package main_frame;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class AppMain {
	
	public static Connection connection;
	public static String filepath;
	
	public static void main(String[] args) {
		JFileChooser fc = new JFileChooser(new File("."));
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		if(file == null)
			return;
		filepath = file.getAbsolutePath();
		
		try {
			establishConnection();
			createTables();
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage() + "\n" + e.getClass());
		}
		
		new MainFrame(connection);
	}
	
	private static void establishConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:" + filepath);
	}
	
	private static void createTables() throws SQLException {
		String[] sqls = {
				"CREATE TABLE IF NOT EXISTS subject (\r\n" + 
				"	id INTEGER PRIMARY KEY,\r\n" + 
				"	name TEXT NOT NULL\r\n" + 
				");",
				"CREATE TABLE IF NOT EXISTS class (\r\n" + 
				"	id INTEGER PRIMARY KEY,\r\n" + 
				"	subject_id INTEGER NOT NULL,\r\n" + 
				"	FOREIGN KEY (subject_id) REFERENCES subject(id) ON UPDATE CASCADE ON DELETE CASCADE\r\n" + 
				");",
				"CREATE TABLE IF NOT EXISTS section (\r\n" + 
				"	id INTEGER PRIMARY KEY,\r\n" + 
				"	class_id INTEGER,\r\n" + 
				"	day INTEGER NOT NULL,\r\n" + 
				"	start INTEGER NOT NULL,\r\n" + 
				"	duration INTEGER NOT NULL,\r\n" + 
				"	FOREIGN KEY (class_id) REFERENCES class(id) ON UPDATE CASCADE ON DELETE CASCADE\r\n" + 
				");",
				"CREATE TABLE IF NOT EXISTS timetable (\r\n" + 
				"	id INTEGER PRIMARY KEY,\r\n" + 
				"	name TEXT NOT NULL\r\n" + 
				");",
				"CREATE TABLE IF NOT EXISTS schedule (\r\n" + 
				"	id INTEGER PRIMARY KEY,\r\n" + 
				"	class_id INTEGER,\r\n" + 
				"	timetable_id INTEGER,\r\n" + 
				"	FOREIGN KEY (class_id) REFERENCES class(id) ON UPDATE CASCADE ON DELETE CASCADE,\r\n" + 
				"	FOREIGN KEY (timetable_id) REFERENCES timetable(id) ON UPDATE CASCADE ON DELETE CASCADE\r\n" + 
				");",
				"PRAGMA foreign_keys = ON;"
		};
		
		Statement st = connection.createStatement();
		for(int i = 0; i<sqls.length; i++) {
			st.execute(sqls[i]);
		}
		st.close();
	}
}
