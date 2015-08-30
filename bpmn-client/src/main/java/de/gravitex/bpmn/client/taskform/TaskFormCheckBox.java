package de.gravitex.bpmn.client.taskform;

import javax.swing.JCheckBox;

public class TaskFormCheckBox extends JCheckBox implements TaskFormComponent
{
	private static final long serialVersionUID = -4158544356979432037L;

	public Object retrieveValue()
	{
		return new Boolean(isSelected());
	}
	
	public void applyValue(Object value)
	{
		// TODO Auto-generated method stub
	}
}