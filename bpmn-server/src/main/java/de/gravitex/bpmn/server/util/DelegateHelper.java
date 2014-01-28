package de.gravitex.bpmn.server.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;

public class DelegateHelper {

	private static final String PARENT_BUSINESS_KEY = "parentBusinessKey";

	public static Map<String, Object> parentLinkVariables(DelegateExecution execution) {
		Map<String, Object> variables = new HashMap<>();
		variables.put(PARENT_BUSINESS_KEY, execution.getProcessBusinessKey());
		return variables;
	}
	
	public static String linkDownToParent(DelegateExecution execution) {
		return (String) execution.getVariable(DelegateHelper.PARENT_BUSINESS_KEY);
	}
	
	public static String generateBusinessKey() {
		Calendar calendar = Calendar.getInstance();
		return "bk"+calendar.get(Calendar.SECOND);
	}
}
