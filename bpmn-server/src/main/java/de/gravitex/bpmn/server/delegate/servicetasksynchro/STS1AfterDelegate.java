package de.gravitex.bpmn.server.delegate.servicetasksynchro;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class STS1AfterDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("STS1AfterDelegate");
	}
}
