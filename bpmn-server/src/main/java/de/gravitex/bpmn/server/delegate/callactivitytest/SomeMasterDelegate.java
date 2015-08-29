package de.gravitex.bpmn.server.delegate.callactivitytest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SomeMasterDelegate implements JavaDelegate
{
	public static final String VAR_SOME_MASTER_VALUE = "someMasterValue";

	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("setting some master value...");
		execution.setVariable(VAR_SOME_MASTER_VALUE, new Integer(12));
	}
}
