package de.gravitex.bpmn.server.delegate.asynchronousinvoice;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.util.OutputFormatter;

public class GenerateInvoiceDelegate implements JavaDelegate
{	
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("GenerateInvoiceDelegate (time : "+OutputFormatter.formatDateTime(new Date())+")");
	}
}
