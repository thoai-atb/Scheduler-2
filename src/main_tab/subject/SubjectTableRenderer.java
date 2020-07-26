package main_tab.subject;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;

public class SubjectTableRenderer implements TableCellRenderer{
	
	public SubjectTableRenderer() {
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JRadioButton button = new JRadioButton();
		button.setOpaque(true);
		button.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Thread.dumpStack();
				table.setValueAt(button.isSelected(), row, column);
			}
		});
		JPanel panel = new JPanel();
		panel.add(button);
		return panel;
	}

}
