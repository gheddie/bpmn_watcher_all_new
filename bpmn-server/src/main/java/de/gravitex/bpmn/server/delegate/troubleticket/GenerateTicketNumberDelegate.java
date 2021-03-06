package de.gravitex.bpmn.server.delegate.troubleticket;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;
import de.gravitex.bpmn.server.processhelper.troubleticket.TroubleTicketHelper;

public class GenerateTicketNumberDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		execution.setVariable(ProcessVariables.TroubleTicket.VAR_TICKET_NUMBER, TroubleTicketHelper.generateTicketNumber());
	}
}