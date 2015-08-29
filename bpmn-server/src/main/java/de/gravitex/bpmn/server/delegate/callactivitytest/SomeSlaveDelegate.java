package de.gravitex.bpmn.server.delegate.callactivitytest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SomeSlaveDelegate implements JavaDelegate
{
	public static final String VAR_SOME_SLAVE_VALUE = "someSlaveValue";
	
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("retrieving some master value : " + execution.getVariable(SomeMasterDelegate.VAR_SOME_MASTER_VALUE));
		execution.setVariable(VAR_SOME_SLAVE_VALUE, new Integer(1234));
	}
}
