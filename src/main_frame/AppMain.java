package main_frame;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class AppMain {
	
	public static void main(String[] args) {
		File directory = new File("MySchedules");
		directory.mkdir();
		File file = new File("MySchedules/default.db");
		String filepath = file.getAbsolutePath();
		
		
		try {
			openMainFrame(filepath);
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage() + "\n" + e.getClass());
		}
		
	}
	
	public static void openMainFrame(String path) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection("jdbc:sqlite:" + path);
		new MainFrame(path, connection);
	}
}
