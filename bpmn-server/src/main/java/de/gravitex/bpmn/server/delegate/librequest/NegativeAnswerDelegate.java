package de.gravitex.bpmn.server.delegate.librequest;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class NegativeAnswerDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		HashMap<String, Object> variables = new HashMap<String, Object>();
		variables.put("libraryApproved", false);
		BpmEngine.getInstance().getProcessEngine().getRuntimeService().correlateMessage("MSG_USAGE_IMP", null, null, variables);
	}
}