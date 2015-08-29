package de.gravitex.bpmn.server.delegate.collaboration.standalone;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class CSSendDelegate implements JavaDelegate
{
	public static final String PARENT_BUSINESS_KEY = "parentBusinessKey";

	public void execute(DelegateExecution execution) throws Exception
	{
		HashMap<String, Object> variables = new HashMap<>();
		variables.put(PARENT_BUSINESS_KEY, execution.getBusinessKey());
		BpmEngine.getInstance().runtime().startProcessInstanceByMessage("MSG_123_RECV", variables);
	}
}
