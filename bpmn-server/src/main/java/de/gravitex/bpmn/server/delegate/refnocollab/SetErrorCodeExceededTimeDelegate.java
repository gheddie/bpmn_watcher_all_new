package de.gravitex.bpmn.server.delegate.refnocollab;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SetErrorCodeExceededTimeDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		execution.setVariable(RefNoCollabVariables.VAR_ERROR_CODE, RefNoCollabVariables.ERROR_CODE_EXC_TIME);
	}
}
