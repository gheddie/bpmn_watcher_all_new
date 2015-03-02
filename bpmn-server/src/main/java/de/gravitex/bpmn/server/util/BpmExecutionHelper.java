package de.gravitex.bpmn.server.util;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class BpmExecutionHelper {
	
	private static final String PARENT_BUSINESS_KEY = "INST_EXEC_PARENT_BUSINESS_KEY";

	/**
	 * Korreliert die angegebene Nachricht unter Mitgabe des
	 * Business Key der aufrufenden Prozess-Instanz.
	 * 
	 * @param execution
	 */
	public static void correlateWithParentBusinessKey(DelegateExecution execution) {
		String parentBusinessKey = execution.getProcessBusinessKey();
		HashMap<String, Object> variables = new HashMap<String, Object>();
		variables.put(PARENT_BUSINESS_KEY, parentBusinessKey);
		BpmEngine.getInstance().runtime().correlateMessage(BpmMessages.TestCollaborationMessages.MESSAGE_SEND, "", variables);
	}
	
	/**
	 * Korreliert eine Nachricht an die Prozess-Instanz zurück, welche durch denjenigen Business Key
	 * identifiziert wird, der an der hier übergebenen (Kind-) Exekution als Variable
	 * {@link BpmExecutionHelper#PARENT_BUSINESS_KEY} hinterlegt ist. Wenn diese Variable
	 * nicht existiert, geschieht hier nichts.
	 * 
	 * @param execution
	 * @param messageIdentifier
	 */
	public static void recorrelateByParentBusinessKey(DelegateExecution execution, String messageIdentifier) {
		String parentBusinessKey = (String) execution.getVariable(PARENT_BUSINESS_KEY);
		if ((parentBusinessKey == null) || (parentBusinessKey.length() == 0)) {
			return;
		}
		BpmEngine.getInstance().runtime().correlateMessage(messageIdentifier, parentBusinessKey);
	}
}
