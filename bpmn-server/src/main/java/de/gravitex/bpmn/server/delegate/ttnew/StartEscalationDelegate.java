package de.gravitex.bpmn.server.delegate.ttnew;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class StartEscalationDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		BpmEngine.getInstance().getProcessEngine().getRuntimeService().correlateMessage("MSG_START_ESCALATION");
	}
}