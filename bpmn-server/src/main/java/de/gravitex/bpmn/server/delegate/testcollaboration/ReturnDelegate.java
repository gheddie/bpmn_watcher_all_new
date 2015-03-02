package de.gravitex.bpmn.server.delegate.testcollaboration;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.util.BpmExecutionHelper;
import de.gravitex.bpmn.server.util.BpmMessages;

public class ReturnDelegate implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		BpmExecutionHelper.recorrelateByParentBusinessKey(execution, BpmMessages.TestCollaborationMessages.MESSAGE_RETURN);
	}
}
