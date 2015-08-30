package de.gravitex.bpmn.client.taskform;

import javax.swing.JTextField;

public class TaskFormTextField extends JTextField implements TaskFormComponent
{
	private static final long serialVersionUID = -6237354744316499920L;

	public Object retrieveValue()
	{
		return getText();
	}
}