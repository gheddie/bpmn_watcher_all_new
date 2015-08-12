package de.gravitex.bpmn.server.delegate.asynchronousinvoice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GenerateInvoiceDelegate implements JavaDelegate
{
	private static final DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
	
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("GenerateInvoiceDelegate (time : "+df.format(new Date())+")");
	}
}
