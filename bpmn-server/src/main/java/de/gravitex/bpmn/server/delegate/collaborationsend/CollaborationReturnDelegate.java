package de.gravitex.bpmn.server.delegate.collaborationsend;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;
import de.gravitex.bpmn.server.util.BpmMessages;

public class CollaborationReturnDelegate implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		BpmEngine.getInstance().runtime().correlateMessage(BpmMessages.CollaborationSendMessages.MESSAGE_COLL_RETURN);
	}
}
