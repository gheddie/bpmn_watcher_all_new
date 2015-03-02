package de.gravitex.bpmn.server.delegate.testcollaboration;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.util.BpmExecutionHelper;

public class SendDelegate implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		BpmExecutionHelper.correlateWithParentBusinessKey(execution);
	}
}
