package de.gravitex.bpmn.server.delegate.refnocollab;

import java.sql.Ref;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class StopRefuelingDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		execution.setVariable(RefNoCollabVariables.VAR_AMOUNT_TO_DEBIT, new Integer(82));
	}
}
