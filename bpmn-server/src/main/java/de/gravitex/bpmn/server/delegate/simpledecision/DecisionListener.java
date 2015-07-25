package de.gravitex.bpmn.server.delegate.simpledecision;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DecisionListener implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("DecisionListener");
		execution.setVariable("value", 2000);
	}
}
