package de.gravitex.bpmn.server.delegate.masterquestion;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class Return1Delegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("Return1Delegate");
		BpmEngine.getInstance().runtime().correlateMessage("MSG_MQ_1", "", null);
	}
}
