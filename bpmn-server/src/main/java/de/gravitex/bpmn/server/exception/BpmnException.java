package de.gravitex.bpmn.server.exception;

import org.camunda.bpm.engine.ProcessEngineException;

public class BpmnException extends Exception {

	private static final long serialVersionUID = 1L;

	public BpmnException(ProcessEngineException e) {
		super(e);
	}
	
	public BpmnException(String message, ProcessEngineException e) {
		super(message, e);
	}
}
