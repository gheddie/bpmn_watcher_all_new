package de.gravitex.bpmn.server.delegate.callactivitytest.multiple;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CamErrorDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		if (execution.getVariable("selectedError") == null)
		{
			throw new Exception("provide variable 'selectedError'!!");	
		}		
	}
}
