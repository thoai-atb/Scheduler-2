package sql_tab;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class QueryUI extends JPanel {
	
	Connection connection;
	JTextArea queryArea;
	JTable resultTable;
	
	public QueryUI(Connection connection) {
		this.connection = connection;
		
		this.setLayout(new BorderLayout());
		
		JPanel queryPanel = new JPanel(new GridBagLayout());
		queryPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 10;
		c.weighty = 10;
		
		JLabel queryLabel = new JLabel("Query");
		c.gridx = 0;
		c.gridy = 0;
		queryPanel.add(queryLabel, c);
		
		queryArea = new JTextArea(5, 30);
		c.gridx = 1;
		queryPanel.add(new JScrollPane(queryArea), c);
		
		JButton runB = new JButton("Run");
		runB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					execute();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		c.gridx = 2;
		queryPanel.add(runB, c);
		this.add(queryPanel, BorderLayout.NORTH);
		
		JPanel resultPanel = new JPanel(new BorderLayout());
		resultPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		resultTable = new JTable();
		resultPanel.add(new JScrollPane(resultTable));
		this.add(resultPanel, BorderLayout.CENTER);
	}
	
	public void execute() throws SQLException {
		Statement st = connection.createStatement();
		String sql = queryArea.getText();
		ResultSet rs = st.executeQuery(sql);
		ResultTableModel rt = new ResultTableModel(rs);
		resultTable.setModel(rt);
	}

}
