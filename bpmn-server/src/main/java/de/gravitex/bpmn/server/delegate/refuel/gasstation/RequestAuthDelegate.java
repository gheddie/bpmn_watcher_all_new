package de.gravitex.bpmn.server.delegate.refuel.gasstation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class RequestAuthDelegate implements JavaDelegate
{
	public void execute(DelegateExecution arg0) throws Exception
	{
		System.out.println("requesting authorization...");
		BpmEngine.getInstance().runtime().correlateMessage("MSG_AUTH_REQUESTED");
	}
}