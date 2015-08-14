package de.gravitex.bpmn.server.delegate.asynchronousinvoiceimproved;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendInvoiceImprovedDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("SendInvoiceDelegate - this will fail...");
		try
		{
			throw new Exception("post office is closed...");	
		} catch (Exception e)
		{
			throw e;
		}
	}
}
