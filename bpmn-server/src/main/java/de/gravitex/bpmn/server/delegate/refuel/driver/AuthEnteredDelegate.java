package de.gravitex.bpmn.server.delegate.refuel.driver;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class AuthEnteredDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("driver entered auth...");
		HashMap<String, Object> variables = new HashMap<>();
		variables.put("pincode", execution.getVariable("pincode"));
		BpmEngine.getInstance().runtime().correlateMessage("MSG_AUTH_DELIVERED");
	}
}
