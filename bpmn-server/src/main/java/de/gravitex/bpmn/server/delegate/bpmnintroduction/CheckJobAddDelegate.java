package de.gravitex.bpmn.server.delegate.bpmnintroduction;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CheckJobAddDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		if (execution.getVariable("revisionCounter") == null)
		{
			execution.setVariable("revisionCounter", new Integer(0));
		}
		Integer oldValue = (Integer) execution.getVariable("revisionCounter");
		Integer newValue = new Integer(++oldValue);
		execution.setVariable("revisionCounter", newValue);
		
		System.out.println("revision counter is now "+newValue+".");
	}
}
