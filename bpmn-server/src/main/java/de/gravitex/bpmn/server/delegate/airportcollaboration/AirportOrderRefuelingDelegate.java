package de.gravitex.bpmn.server.delegate.airportcollaboration;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class AirportOrderRefuelingDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		BpmEngine.getInstance().runtime().correlateMessage("MSG_ORD_REC");
	}
}
