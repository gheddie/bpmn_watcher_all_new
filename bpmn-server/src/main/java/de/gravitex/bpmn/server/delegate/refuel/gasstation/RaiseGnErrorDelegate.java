package de.gravitex.bpmn.server.delegate.refuel.gasstation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class RaiseGnErrorDelegate implements JavaDelegate
{

	@Override
	public void execute(DelegateExecution execution) throws Exception
	{
		BpmEngine.getInstance().runtime().correlateMessage("MSG_GN_DECLINED");
		increaseErrorCounter(execution);
	}

	private void increaseErrorCounter(DelegateExecution execution)
	{
		if (execution.getVariable("gnErrorCounter") == null)
		{
			execution.setVariable("gnErrorCounter", new Integer(1));
		}
		else
		{
			int tmp = (int) execution.getVariable("gnErrorCounter");
			tmp++;
			System.out.println("increasing gn error counter to "+tmp+"...");
			execution.setVariable("gnErrorCounter", tmp);
		}
	}
}
