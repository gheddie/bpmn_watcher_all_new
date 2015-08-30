package de.gravitex.bpmn.client.taskform;

import javax.swing.JTextField;

public class TaskFormNumericTextField extends JTextField implements TaskFormComponent
{
	private static final long serialVersionUID = 3969212626524454622L;

	public Object retrieveValue()
	{
		return Integer.parseInt(getText());
	}

	public void applyValue(Object value)
	{
		if (value == null)
		{
			setText("");
		}
		setText(String.valueOf(value));
	}
}