package main_frame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class AboutFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public AboutFrame() {
		super("About Scheduler " + AppMain.version);
		getContentPane().setBackground(new Color(245, 222, 179));
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JTextArea txtrMadeByThoai = new JTextArea();
		txtrMadeByThoai.setFont(new Font("Microsoft Sans Serif", Font.ITALIC, 17));
		txtrMadeByThoai.setBackground(new Color(245, 222, 179));
		txtrMadeByThoai.setEditable(false);
		txtrMadeByThoai.setWrapStyleWord(true);
		txtrMadeByThoai.setText("This software is for students who do not want to arrange their schedule manually.");
		txtrMadeByThoai.setBounds(34, 36, 312, 70);
		txtrMadeByThoai.setLineWrap(true);
		getContentPane().add(txtrMadeByThoai);
		
		JLabel lblNewLabel = new JLabel("Made by Thoai Ly");
		lblNewLabel.setFont(new Font("STZhongsong", Font.ITALIC, 15));
		lblNewLabel.setBounds(34, 104, 182, 42);
		getContentPane().add(lblNewLabel);
		this.setVisible(true);
	}
}
