package de.gravitex.bpmn.server.dto;

import java.io.Serializable;

public class VariableInstanceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private Object value;

	private VariableState variableState;

	public VariableInstanceDTO(String name, Object value, VariableState variableState) {
		super();
		this.name = name;
		this.value = value;
		this.variableState = variableState;
	}
	
	public String getName() {
		return name;
	}
	
	public Object getValue() {
		return value;
	}
	
	public VariableState getVariableState() {
		return variableState;
	}
	
	public String toString() {
		return getClass().getSimpleName() + "[name:"+name+"|value:"+value+"]";
	}

	public boolean isDeployed() {
		return (variableState.equals(VariableState.DEPLOYED));
	}
}
