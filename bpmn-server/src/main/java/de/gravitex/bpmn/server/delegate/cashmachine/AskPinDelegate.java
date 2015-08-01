package de.gravitex.bpmn.server.delegate.cashmachine;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class AskPinDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("AskPinDelegate");
		BpmEngine.getInstance().runtime().correlateMessage(CashMachineMessages.MSG_PIN_ASKED);
	}
}
