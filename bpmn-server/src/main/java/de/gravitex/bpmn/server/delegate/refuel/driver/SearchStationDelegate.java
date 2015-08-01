package de.gravitex.bpmn.server.delegate.refuel.driver;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SearchStationDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		execution.setVariable("range", new Integer(100));
		execution.setVariable("distance", new Integer(80));
	}
}
