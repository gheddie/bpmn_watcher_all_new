package de.gravitex.bpmn.server.delegate.ttnew;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;

public class SendEscalationMailDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		// (1) send mail
		//...
		// (2) increase escalation level
		int newEscalationLevel = ((Integer) execution.getVariable(ProcessVariables.TroubleTicketNew.VAR_ESCALATION_LEVEL)) + 1;
		execution.setVariable(ProcessVariables.TroubleTicketNew.VAR_ESCALATION_LEVEL, new Integer(newEscalationLevel));
	}
}