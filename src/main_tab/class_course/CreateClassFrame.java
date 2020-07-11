package main_tab.class_course;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;

public class CreateClassFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private ClassPanel panel;
	private String subjectID;
	private String[] daysOfWeek = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	private String[] starts = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
	private String[] durations = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
	private JComboBox<String> day1, start1, dur1, day2, start2, dur2;
	private JRadioButton rdbtnNewRadioButton;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CreateClassFrame(ClassPanel panel, String subjectID){
		super("Create New Class");
		if(subjectID == null) {
			JOptionPane.showMessageDialog(null, "NULL SUBJECT");
			return;
		}
		this.subjectID = subjectID;
		this.panel = panel;
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 270, 0, 0};
		gridBagLayout.rowHeights = new int[]{55, 0, 0, 0, 0, 0, 0, 0, 55, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		mainPanel.setLayout(gridBagLayout);
		
		JLabel lblNewLabel_3 = new JLabel("NEW CLASS");
		lblNewLabel_3.setFont(new Font("UD Digi Kyokasho NP-B", Font.PLAIN, 15));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 0;
		mainPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("Day");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		mainPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		day1 = new JComboBox(daysOfWeek);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		mainPanel.add(day1, gbc_comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Start");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		mainPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		start1 = new JComboBox(starts);
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 2;
		mainPanel.add(start1, gbc_comboBox_1);
		
		JLabel lblNewLabel_2 = new JLabel("Duration");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 3;
		mainPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		dur1 = new JComboBox(durations);
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 2;
		gbc_comboBox_2.gridy = 3;
		mainPanel.add(dur1, gbc_comboBox_2);
		
		rdbtnNewRadioButton = new JRadioButton("Two-section Class");
		rdbtnNewRadioButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				boolean state = rdbtnNewRadioButton.isSelected();
				day2.setEnabled(state);
				start2.setEnabled(state);
				dur2.setEnabled(state);
			}
		});
		GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
		gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton.gridx = 2;
		gbc_rdbtnNewRadioButton.gridy = 4;
		mainPanel.add(rdbtnNewRadioButton, gbc_rdbtnNewRadioButton);
		
		JLabel lblNewLabel_5 = new JLabel("Day 2");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 1;
		gbc_lblNewLabel_5.gridy = 5;
		mainPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		day2 = new JComboBox(daysOfWeek);
		day2.setEnabled(false);
		GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
		gbc_comboBox_3.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3.gridx = 2;
		gbc_comboBox_3.gridy = 5;
		mainPanel.add(day2, gbc_comboBox_3);
		
		JLabel lblNewLabel_6 = new JLabel("Start 2");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 1;
		gbc_lblNewLabel_6.gridy = 6;
		mainPanel.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		start2 = new JComboBox(starts);
		start2.setEnabled(false);
		GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
		gbc_comboBox_4.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_4.gridx = 2;
		gbc_comboBox_4.gridy = 6;
		mainPanel.add(start2, gbc_comboBox_4);
		
		JLabel lblNewLabel_7 = new JLabel("Duration 2");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 1;
		gbc_lblNewLabel_7.gridy = 7;
		mainPanel.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		dur2 = new JComboBox(durations);
		dur2.setEnabled(false);
		GridBagConstraints gbc_comboBox_5 = new GridBagConstraints();
		gbc_comboBox_5.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_5.gridx = 2;
		gbc_comboBox_5.gridy = 7;
		mainPanel.add(dur2, gbc_comboBox_5);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 8;
		mainPanel.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton = new JButton("Create");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 8;
		mainPanel.add(btnNewButton, gbc_btnNewButton);
		this.setVisible(true);
	}
	
	private void submit() {
		boolean twoSections = rdbtnNewRadioButton.isSelected();
		String[][] sections = new String[twoSections? 2 : 1][];
		sections[0] = new String[] {(String) day1.getSelectedItem(), (String) start1.getSelectedItem(), (String) dur1.getSelectedItem()};
		if(twoSections) {
			sections[1] = new String[] {(String) day2.getSelectedItem(), (String) start2.getSelectedItem(), (String) dur2.getSelectedItem()};
		}
		
		try {
			panel.addNewClass(subjectID, sections);
			this.dispose();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
