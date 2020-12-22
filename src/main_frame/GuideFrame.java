package main_frame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GuideFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public GuideFrame() {
		super("How to use Scheduler " + AppMain.version);
		getContentPane().setBackground(new Color(255, 222, 173));
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JTextArea txtrAfterHavingA = new JTextArea();
		txtrAfterHavingA.setBounds(10, 28, 464, 373);
		txtrAfterHavingA.setBackground(new Color(255, 222, 173));
		txtrAfterHavingA.setFont(new Font("Leelawadee", Font.PLAIN, 18));
		txtrAfterHavingA.setText("To use Scheduler, first create the Subjects, then create some classes for that Subjects, and go to Action -> Arrange to start scheduling."
				+ "\n\nAfter having a list of time tables, you can press the > or < buttons to go through the list, and press Save to save the current time table for later reviews."
				+ "\n\nThe saved time tables will be shown on the right side of the screen, press Show to see the content.");
		txtrAfterHavingA.setLineWrap(true);
		txtrAfterHavingA.setWrapStyleWord(true);
		getContentPane().add(txtrAfterHavingA);
		this.setVisible(true);
	}

}
