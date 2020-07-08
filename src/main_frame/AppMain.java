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
		System.out.println(filepath);	
		
		
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
		String[] tables = {
				"CREATE TABLE IF NOT EXISTS subject (\r\n" + 
				"	id INTEGER PRIMARY KEY,\r\n" + 
				"	name TEXT NOT NULL\r\n" + 
				");",
				"CREATE TABLE IF NOT EXISTS section (\r\n" + 
				"	id INTEGER PRIMARY KEY,\r\n" + 
				"	day INTEGER NOT NULL,\r\n" + 
				"	start INTEGER NOT NULL,\r\n" + 
				"	duration INTEGER NOT NULL\r\n" + 
				");",
				"CREATE TABLE IF NOT EXISTS class (\r\n" + 
				"	id INTEGER PRIMARY KEY,\r\n" + 
				"	subject_id INTEGER NOT NULL,\r\n" + 
				"	sectionA INTEGER,\r\n" + 
				"	sectionB INTEGER\r\n" + 
				");"
		};
		
		Statement st = connection.createStatement();
		for(int i = 0; i<tables.length; i++) {
			st.execute(tables[i]);
		}
		st.close();
	}
}
