package de.gravitex.bpmn.server.delegate.callactivitytest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class M2ResultDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("received value from slave : " + execution.getVariable(SomeSlaveDelegate.VAR_SOME_SLAVE_VALUE));
	}
}
