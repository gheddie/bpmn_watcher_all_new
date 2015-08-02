package de.gravitex.bpmn.server.delegate.refnocollab;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ActivatePumpDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("ActivatePumpDelegate");
	}
}
