package de.gravitex.bpmn.server.delegate.creditappliance;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GrantApplianceDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("boohoo, appliance for '"+execution.getVariable("requestedAmount")+"'$ was granted...");
	}
}
