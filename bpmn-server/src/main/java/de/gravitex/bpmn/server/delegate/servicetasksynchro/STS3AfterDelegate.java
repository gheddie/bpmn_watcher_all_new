package de.gravitex.bpmn.server.delegate.servicetasksynchro;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class STS3AfterDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("STS3AfterDelegate");
	}
}
