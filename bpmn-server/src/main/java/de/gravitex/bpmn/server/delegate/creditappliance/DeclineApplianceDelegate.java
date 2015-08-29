package de.gravitex.bpmn.server.delegate.creditappliance;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DeclineApplianceDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("sorry, appliance for '"+execution.getVariable("requestedAmount")+"'$ was declined...");
	}
}
