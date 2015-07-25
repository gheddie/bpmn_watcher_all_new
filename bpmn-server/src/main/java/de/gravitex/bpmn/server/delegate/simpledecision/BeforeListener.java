package de.gravitex.bpmn.server.delegate.simpledecision;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class BeforeListener implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("BeforeListener");
		execution.setVariable("value", 1000);
	}
}
