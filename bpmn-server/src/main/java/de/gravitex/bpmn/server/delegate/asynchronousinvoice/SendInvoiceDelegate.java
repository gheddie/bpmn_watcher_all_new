package de.gravitex.bpmn.server.delegate.asynchronousinvoice;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.processhelper.asynchronousinvoice.AsyncInvoiceHelper;

public class SendInvoiceDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("SendInvoiceDelegate - this will fail...");
		if (!(AsyncInvoiceHelper.isPostOfficeOpen()))
		{
			throw new Exception("post office is closed...");	
		}
	}
}
