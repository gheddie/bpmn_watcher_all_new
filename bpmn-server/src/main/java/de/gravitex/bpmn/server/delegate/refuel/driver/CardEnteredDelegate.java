package de.gravitex.bpmn.server.delegate.refuel.driver;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class CardEnteredDelegate implements JavaDelegate
{
	public void execute(DelegateExecution arg0) throws Exception
	{
		System.out.println("driver entered card...");
		BpmEngine.getInstance().runtime().correlateMessage("MSG_EC_DETECTED");
	}
}
