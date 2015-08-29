package de.gravitex.bpmn.server.delegate.collaboration.standalone;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class CSAnswerDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		BpmEngine.getInstance().runtime().correlateMessage("MSG_234_ANSW", (String) execution.getVariable(CSSendDelegate.PARENT_BUSINESS_KEY));
	}
}
