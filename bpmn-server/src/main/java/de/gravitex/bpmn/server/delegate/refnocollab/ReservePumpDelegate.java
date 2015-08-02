package de.gravitex.bpmn.server.delegate.refnocollab;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.h2.util.IntArray;

public class ReservePumpDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		if (execution.getVariable(RefNoCollabVariables.VAR_CHOSEN_PUMP_NO) == null)
		{
			throw new Exception("pump no must be NULL!!");
		}
		if (execution.getVariable(RefNoCollabVariables.VAR_CHOSEN_PUMP_NO).equals(new Integer(4)))
		{
			throw new Exception("pump no 4 is already occupied!!");
		}
	}
}
