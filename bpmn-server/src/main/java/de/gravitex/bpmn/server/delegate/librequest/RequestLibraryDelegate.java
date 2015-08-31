package de.gravitex.bpmn.server.delegate.librequest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.singleton.BpmEngine;

public class RequestLibraryDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		if (execution.getVariable("libraryName") == null)
		{
			throw new Exception("library name must be set for starting request process!!");
		}
		BpmEngine.getInstance().getProcessEngine().getRuntimeService().correlateMessage("MSG_CHECK_LIC");
	}
}