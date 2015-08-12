package de.gravitex.bpmn.server.delegate.asynchronousinvoice;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendInvoiceDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("SendInvoiceDelegate - this will fail...");
		throw new Exception("post office is closed...");
	}
}
