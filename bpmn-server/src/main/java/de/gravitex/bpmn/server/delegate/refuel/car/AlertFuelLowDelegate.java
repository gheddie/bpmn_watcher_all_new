package de.gravitex.bpmn.server.delegate.refuel.car;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;
import de.gravitex.bpmn.server.util.BpmMessages;

public class AlertFuelLowDelegate implements JavaDelegate
{
	public void execute(DelegateExecution e) throws Exception
	{
		System.out.println("car is alerting low fuel...");
		BpmEngine.getInstance().runtime().correlateMessage("MSG_FUEL_LOW");
	}
}