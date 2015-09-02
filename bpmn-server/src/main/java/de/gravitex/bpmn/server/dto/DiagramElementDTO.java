package de.gravitex.bpmn.server.dto;

import java.io.Serializable;

import org.camunda.bpm.engine.repository.DiagramElement;

public class DiagramElementDTO implements Serializable
{
	private static final long serialVersionUID = -983640769781235220L;
	
	private DiagramElement element;
	
	private String bpmn2TypeName;

	public DiagramElementDTO(DiagramElement element, String bpmn2TypeName)
	{
		super();
		this.element = element;
		this.bpmn2TypeName = bpmn2TypeName;
	}
	
	public DiagramElement getElement()
	{
		return element;
	}
	
	public String getBpmn2TypeName()
	{
		return bpmn2TypeName;
	}
}