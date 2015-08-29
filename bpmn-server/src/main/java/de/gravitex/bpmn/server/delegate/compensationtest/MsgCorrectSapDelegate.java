package de.gravitex.bpmn.server.delegate.compensationtest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class MsgCorrectSapDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		BpmEngine.getInstance().getProcessEngine().getRuntimeService().correlateMessage("MSG_CORRECT_SAP");
	}
}