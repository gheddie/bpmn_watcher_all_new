package de.gravitex.bpmn.server.exception;

import org.camunda.bpm.engine.ProcessEngineException;

public class BpmnPropertyException extends BpmnException
{
	private static final long serialVersionUID = 646887693953555429L;
	
	private String propertyName;

	public BpmnPropertyException(ProcessEngineException e, String propertyName)
	{
		super(e);
		this.propertyName = propertyName;
	}
	
	public String getPropertyName()
	{
		return propertyName;
	}
}
