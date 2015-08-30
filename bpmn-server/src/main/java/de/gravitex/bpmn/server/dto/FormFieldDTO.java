package de.gravitex.bpmn.server.dto;

import java.io.Serializable;

public class FormFieldDTO implements Serializable
{
	private static final long serialVersionUID = 719434130782633553L;
	
	private String variableName;

	private String typeName;

	private Object defaultValue;
	
	public String getVariableName()
	{
		return variableName;
	}
	
	public void setVariableName(String variableName)
	{
		this.variableName = variableName;
	}
	
	public String getTypeName()
	{
		return typeName;
	}

	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}

	public Object getDefaultValue()
	{
		return defaultValue;
	}
	
	public void setDefaultValue(Object defaultValue)
	{
		this.defaultValue = defaultValue;
	}
}