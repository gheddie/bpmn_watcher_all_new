package de.gravitex.bpmn.server.delegate.ttnew;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;
import de.gravitex.bpmn.server.processhelper.troubleticket.TroubleTicketHelper;

public class CalculateEscalationTimeDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		execution.setVariable(ProcessVariables.TroubleTicketNew.VAR_ESCALATION_TIME,
				TroubleTicketHelper.calculateNextEscalationTime((Integer) execution.getVariable(ProcessVariables.TroubleTicketNew.VAR_ESCALATION_LEVEL)));
	}
}