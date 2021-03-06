package de.gravitex.bpmn.server.delegate.masterquestion;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class DoSomethingDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		BpmEngine.getInstance().runtime().correlateMessage("MSG_MQ_START", "", null);
	}
}
