package de.gravitex.bpmn.server.delegate.refnocollab;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;
import de.gravitex.bpmn.server.processhelper.refueling.RefuelingHelper;

public class StartRefuelingProcessDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println("StartRefuelingProcessDelegate");
		execution.setVariable(ProcessVariables.RefNoCollabVars.VAR_REFUELING_PROCESS_ID, RefuelingHelper.createRefueling().getId());
	}
}
