package de.gravitex.bpmn.server.delegate.librequest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendUserMailDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		if ((boolean) execution.getVariable("libraryApproved"))
		{
			System.out.println("Boohoo...library "+execution.getVariable("libraryName")+" approved!!");
		}
		else
		{
			System.out.println("Sorry...library "+execution.getVariable("libraryName")+" declined!!");
		}
	}
}