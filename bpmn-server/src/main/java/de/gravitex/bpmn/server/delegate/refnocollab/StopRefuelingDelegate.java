package de.gravitex.bpmn.server.delegate.refnocollab;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;

public class StopRefuelingDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		execution.setVariable(ProcessVariables.RefNoCollabVars.VAR_AMOUNT_TO_DEBIT, new Integer(82));
	}
}
