package de.gravitex.bpmn.server.delegate.asynchronousinvoiceimproved;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;

public class StartInvoiceProcessDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		execution.setVariable(ProcessVariables.SendInvoiceImproved.SEND_ERROR_COUNTER, new Integer(0));
	}
}
