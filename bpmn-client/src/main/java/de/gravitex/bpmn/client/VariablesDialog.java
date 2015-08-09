/*
 * Created by JFormDesigner on Mon Jan 13 12:47:14 CET 2014
 */

package de.gravitex.bpmn.client;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.gravitex.bpmn.client.exception.VariableParsingException;
import de.gravitex.bpmn.client.parser.VariableParser;
import de.gravitex.bpmn.server.dto.VariableInstanceDTO;
import de.gravitex.bpmn.server.dto.VariableState;

/**
 * @author User #1
 */
public class VariablesDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private VariableInstanceDTO variableInstance;
	
	public VariablesDialog(JFrame owner, int x, int y) {
		super(owner);
		initComponents();
		putListeners();
		setSize(800, 600);
		setLocation(x, y);
		setModal(true);
		fillTypes();
		setVisible(true);
	}

	private void putListeners() {
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object parsedValue;
				try {
					parsedValue = VariableParser.parseValue((VariablesType) cbVariableType.getSelectedItem(), tfVariableValue.getText());
					variableInstance = new VariableInstanceDTO(tfVariableName.getText(), parsedValue, VariableState.STAGED);
					dispose();
				} catch (VariableParsingException e1) {
					JOptionPane.showMessageDialog(VariablesDialog.this, "Wert konnte nicht geparsed werden.");
				}
			}
		});
		//---
		btnAbort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void fillTypes() {		
		Object[] items = new Object[] {VariablesType.BOOLEAN, VariablesType.STRING, VariablesType.DATE, VariablesType.NUMERIC};
		DefaultComboBoxModel model = new DefaultComboBoxModel(items);				
		cbVariableType.setModel(model);
	}

	public VariablesDialog(JDialog owner, int x, int y) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		lblVariableName = new JLabel();
		tfVariableName = new JTextField();
		lblVariableType = new JLabel();
		cbVariableType = new JComboBox();
		lblVariableValue = new JLabel();
		tfVariableValue = new JTextField();
		btnOk = new JButton();
		btnAbort = new JButton();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};

		//---- lblVariableName ----
		lblVariableName.setText("Name:");
		contentPane.add(lblVariableName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		contentPane.add(tfVariableName, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- lblVariableType ----
		lblVariableType.setText("Typ:");
		contentPane.add(lblVariableType, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		contentPane.add(cbVariableType, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- lblVariableValue ----
		lblVariableValue.setText("Wert:");
		contentPane.add(lblVariableValue, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		contentPane.add(tfVariableValue, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- btnOk ----
		btnOk.setText("OK");
		contentPane.add(btnOk, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 5), 0, 0));

		//---- btnAbort ----
		btnAbort.setText("Abbrechen");
		contentPane.add(btnAbort, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel lblVariableName;
	private JTextField tfVariableName;
	private JLabel lblVariableType;
	private JComboBox cbVariableType;
	private JLabel lblVariableValue;
	private JTextField tfVariableValue;
	private JButton btnOk;
	private JButton btnAbort;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public VariableInstanceDTO getVariable() {
		return variableInstance;
	}
}
