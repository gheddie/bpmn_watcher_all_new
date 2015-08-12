package de.gravitex.bpmn.server.delegate.servicetasksynchro;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class STS3Delegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		STSHelper.countToInfinity("STS3");
	}
}
