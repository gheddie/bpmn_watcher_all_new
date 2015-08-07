package de.gravitex.bpmn.server.delegate.exportrequest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.processhelper.exportrequest.ExportRequestDbConnector;
import de.gravitex.bpmn.server.processhelper.exportrequest.ExportRequestStatus;

public class DeclineRequestDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		ExportRequestDbConnector.updateRequestStatus(execution.getProcessBusinessKey(), ExportRequestStatus.DECLINED);
	}
}
