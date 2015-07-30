package de.gravitex.bpmn.server.delegate.refuel.gasstation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ValidateAuthDelegate implements JavaDelegate
{
	public void execute(DelegateExecution arg0) throws Exception
	{
		System.out.println("validating auth...");
	}
}
