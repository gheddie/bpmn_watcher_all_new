package de.gravitex.bpmn.server.delegate.refnocollab;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;

public class ActivatePumpDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("ActivatePumpDelegate [Process-ID:"+execution.getVariable(ProcessVariables.RefNoCollabVars.VAR_REFUELING_PROCESS_ID)+"]");
	}
}
