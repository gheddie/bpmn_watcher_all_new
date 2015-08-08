package de.gravitex.bpmn.server.delegate.exportrequest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.processhelper.exportrequest.ExportRequestHelper;
import de.gravitex.bpmn.server.singleton.BpmEngine;
import de.gravitex.bpmn.server.test.ExportRequest;

public class PollRequestsDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		for (ExportRequest request : ExportRequestHelper.queryUnprocessedExportRequests())
		{
			System.out.println("PollRequestsDelegate : starting eval process by business key '"+request+"'.");
			BpmEngine.getInstance().runtime().correlateMessage(ExportRequestMessages.MSG_START_REQ_EVAL, request.getRequestNumber());
		}
	}
}
