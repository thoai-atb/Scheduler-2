package main_frame;
import java.awt.BorderLayout;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import main_tab.SubjectPanel;
import sql_tab.QueryUI;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	Connection con;
	
	public MainFrame (Connection con) {
		super("Scheduler");
		this.con = con;
		
		this.setLayout(new BorderLayout());
		
		JTabbedPane tp = new JTabbedPane();
		tp.add(new SubjectPanel(con), "Control");
		tp.add(new QueryUI(con), "SQL");
		this.add(tp);
		
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
