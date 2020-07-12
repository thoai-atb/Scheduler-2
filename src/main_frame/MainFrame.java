package main_frame;
import java.awt.BorderLayout;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import main_tab.MainPanel;
import sql_tab.QueryUI;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	Connection con;
	
	public MainFrame (Connection con) {
		super("Scheduler");
		this.con = con;
		
		this.setLayout(new BorderLayout());
		
		JTabbedPane tp = new JTabbedPane();
		tp.add(new MainPanel(con), "Control");
		tp.add(new QueryUI(con), "SQL");
		this.add(tp);
		
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
