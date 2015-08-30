package de.gravitex.bpmn.client.taskform;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import de.gravitex.bpmn.client.DialogResultState;
import de.gravitex.bpmn.server.dto.FormFieldDTO;

public class TaskFormDialog extends JDialog
{
	private static final int COMPONENT_HEIGHT = 30;
	
	private static final int VERTICAL_GAP = 5;
	
	private static final int HORIZONTAL_GAP = 5;
	
	private static final int LABEL_WIDTH = 200;
	
	private static final int COMP_WIDTH = 500;
	
	private static final int X_INSET = 20;

	private static final long serialVersionUID = 2661151087684692572L;
	
	private List<FormFieldDTO> formFields;
	
	private JButton okButton;
	
	private JButton abortButton;

	private HashMap<String, Object> results;
	
	private HashMap<String, TaskFormComponent> registeredComponents;

	private DialogResultState resultState;

	public TaskFormDialog(List<FormFieldDTO> formFields, JFrame owner, int x, int y)
	{
		super();
		setSize(800, 600);
		setLocation(x, y);
		this.formFields = formFields;
		setLayout(null);
		putComponents();
		putListeners();
		setResizable(false);
		setModal(true);
	}

	private void putListeners()
	{
		//OK
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				resultState = DialogResultState.OK;
				parseValues();
				setVisible(false);
			}
		});
		//Abbrechen
		abortButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				resultState = DialogResultState.ABORT;
				dispose();
			}
		});
	}
	
	private void parseValues()
	{
		results = new HashMap<>();
		for (FormFieldDTO formField : formFields)
		{
			results.put(formField.getVariableName(), registeredComponents.get(formField.getVariableName()).retrieveValue());
		}
	}

	private void putComponents()
	{
		registeredComponents = new HashMap<>();
		TaskFormComponent valueComponent = null;
		JLabel label = null;
		int yPos = 20;
		for (FormFieldDTO formField : formFields)
		{
			//label
			label = new JLabel(formField.getVariableName());
			label.setBounds(X_INSET, yPos, LABEL_WIDTH, COMPONENT_HEIGHT);
			add(label);
			//component
			valueComponent = getComponentByTypeName(formField.getTypeName());
			((Component) valueComponent).setBounds(X_INSET + LABEL_WIDTH + HORIZONTAL_GAP, yPos, COMP_WIDTH, COMPONENT_HEIGHT);
			add((Component) valueComponent);	
			registeredComponents.put(formField.getVariableName(), valueComponent);
			yPos += COMPONENT_HEIGHT + VERTICAL_GAP;
		}
		//ok and abort
		okButton = new JButton("OK");
		okButton.setBounds(50, getHeight()-100, 75, COMPONENT_HEIGHT);
		add(okButton);
		abortButton = new JButton("Abbrechen");
		abortButton.setBounds(150, getHeight()-100, 150, COMPONENT_HEIGHT);
		add(abortButton);
	}

	private TaskFormComponent getComponentByTypeName(String typeName)
	{
		switch (typeName) {
		case "string":
			return new TaskFormTextField();
		case "boolean":
			return new TaskFormCheckBox();
		default:
			return new TaskFormTextField();
		}
	}

	public HashMap<String, Object> getResult()
	{
		return results;
	}

	public DialogResultState getResultState()
	{
		return resultState;
	}
}