package main_frame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import main_tab.MainPanel;
import sql_tab.QueryUI;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Connection con;
	private JMenuItem newI = new JMenuItem("New");
	private JMenuItem saveI = new JMenuItem("Save");
	private JMenuItem openI = new JMenuItem("Open");
	private boolean saved = true;
	
	public MainFrame (String path, Connection con) throws SQLException {
		super(path + " - Scheduler 2");
		this.con = con;
		createTables();
		
		this.setLayout(new BorderLayout());
		JMenu menu = new JMenu("File");
		newI.addActionListener(this);
		saveI.addActionListener(this);
		openI.addActionListener(this);
		menu.add(newI);
		menu.add(saveI);
		menu.add(openI);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		
		JTabbedPane tp = new JTabbedPane();
		tp.add(new MainPanel(this), "Control");
		tp.add(new QueryUI(con), "SQL");
		this.add(tp);
		
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!saveWarning())
					return;
				System.exit(0);
			}
		});
	}
	
	public Connection getConnection(boolean update) {
		if(!update)
			return con;
		
		if(saved) {
			Statement st;
			try {
				st = con.createStatement();
				st.execute("BEGIN TRANSACTION;");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		saved = false;
		return con;
	}
	
	public void nnew() {
		if(!saveWarning())
			return;
		String name = JOptionPane.showInputDialog("Enter file's name (ex: juice)");
		if(name == null)
			return;
		File file = new File("MySchedules/" + name + ".db");
		String filepath = file.getAbsolutePath();
		this.dispose();
		try {
			AppMain.openMainFrame(filepath);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void dispose() {
		if(saveWarning())
			super.dispose();
	}
	
	public void save() throws SQLException {
		if(saved) {
			JOptionPane.showMessageDialog(null, "Already saved");
			return;
		}
		
		String sql = "COMMIT;";
		Statement st = con.createStatement();
		st.execute(sql);
		JOptionPane.showMessageDialog(null, "Schedule Saved");
		saved = true;
	}
	
	public void discardChanges() throws SQLException {
		String sql = "ROLLBACK;";
		Statement st = con.createStatement();
		st.execute(sql);
	}
	
	public void open() {
		if(!saveWarning())
			return;

		JFileChooser fc = new JFileChooser(new File("."));
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		if(file == null)
			return;
		String filepath = file.getAbsolutePath();
		if(filepath == null)
			return;
		this.dispose();
		try {
			AppMain.openMainFrame(filepath);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean saveWarning() {
		if(saved)
			return true;
		int option = JOptionPane.showOptionDialog(null, "Save Changes?", "WARNING", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
		try {
			if(option == JOptionPane.CANCEL_OPTION)
				return false;
			if(option == JOptionPane.YES_OPTION)
				save();
			else if(option == JOptionPane.NO_OPTION)
				discardChanges();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Object juice = e.getSource();
			if(juice == newI) {
				nnew();
			}else if(juice == openI) {
				open();
			}else if(juice == saveI) {
				save();
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}
	
	private void createTables() throws SQLException {
		String[] sqls = {
				"CREATE TABLE IF NOT EXISTS subject (\r\n" + 
				"	id INTEGER PRIMARY KEY,\r\n" + 
				"	name TEXT NOT NULL,\r\n" + 
				"	included INTEGER DEFAULT 1,\r\n" + 
				"	required INTEGER DEFAULT 1\r\n" + 
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
		
		Statement st = con.createStatement();
		for(int i = 0; i<sqls.length; i++) {
			st.execute(sqls[i]);
		}
		st.close();
	}

}
