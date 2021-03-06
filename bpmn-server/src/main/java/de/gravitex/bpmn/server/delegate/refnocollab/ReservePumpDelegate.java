package de.gravitex.bpmn.server.delegate.refnocollab;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;
import de.gravitex.bpmn.server.processhelper.refueling.RefuelingHelper;

public class ReservePumpDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		if (execution
				.getVariable(ProcessVariables.RefNoCollabVars.VAR_CHOSEN_PUMP_NO) == null)
		{
			throw new Exception("pump no must be NULL!!");
		}
		RefuelingHelper
		.reservePump(
				(Long) execution
						.getVariable(ProcessVariables.RefNoCollabVars.VAR_REFUELING_PROCESS_ID),
				(int) execution
						.getVariable(ProcessVariables.RefNoCollabVars.VAR_CHOSEN_PUMP_NO));
	}
}
