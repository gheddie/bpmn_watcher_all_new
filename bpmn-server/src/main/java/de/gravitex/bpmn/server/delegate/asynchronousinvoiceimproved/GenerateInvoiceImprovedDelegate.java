package de.gravitex.bpmn.server.delegate.asynchronousinvoiceimproved;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;
import de.gravitex.bpmn.server.util.OutputFormatter;

public class GenerateInvoiceImprovedDelegate implements JavaDelegate
{	
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("GenerateInvoiceDelegate (time : "+OutputFormatter.formatDateTime(new Date())+")");
		increaseErrorCounter(execution);
	}
	
	private void increaseErrorCounter(DelegateExecution execution)
	{
		Integer oldErrorCounter = (Integer) execution.getVariable(ProcessVariables.SendInvoiceImproved.SEND_ERROR_COUNTER);
		Integer newErrorCounter = (oldErrorCounter+1);
		System.out.println("increased error counter to "+newErrorCounter+"...");
		execution.setVariable(ProcessVariables.SendInvoiceImproved.SEND_ERROR_COUNTER, newErrorCounter);
	}
}
