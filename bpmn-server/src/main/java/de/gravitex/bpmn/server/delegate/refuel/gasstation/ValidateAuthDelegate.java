package de.gravitex.bpmn.server.delegate.refuel.gasstation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ValidateAuthDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		String pincode = (String) execution.getVariable("pincode");
		System.out.println("validating auth : " + pincode);
		if (pincode == null)
		{
			pincode = "2345";
		}
		if (pincode.equals("1234"))
		{
			execution.setVariable("gnChecked", true);
			System.out.println("pincode "+pincode+" was ok...");
		} else
		{
			execution.setVariable("gnChecked", false);
			System.out.println("pincode "+pincode+" was NOT ok...");
		}
	}
}
