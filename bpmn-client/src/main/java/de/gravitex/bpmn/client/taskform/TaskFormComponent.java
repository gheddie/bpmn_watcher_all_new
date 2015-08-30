package de.gravitex.bpmn.client.taskform;

public interface TaskFormComponent
{
	Object retrieveValue();

	void applyValue(Object value);
}