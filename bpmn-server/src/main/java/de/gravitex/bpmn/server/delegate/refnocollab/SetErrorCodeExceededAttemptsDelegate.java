package de.gravitex.bpmn.server.delegate.refnocollab;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;
import de.gravitex.bpmn.server.commonvalues.ProcessVariables.RefNoCollabVars;
import de.gravitex.bpmn.server.persistence.entity.RefuelingStatus;
import de.gravitex.bpmn.server.processhelper.refueling.RefuelingHelper;

public class SetErrorCodeExceededAttemptsDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		execution.setVariable(RefNoCollabVars.VAR_ERROR_CODE, ProcessVariables.RefNoCollabVars.ERROR_CODE_EXC_ATT);
		RefuelingHelper.finishProcess((Long) execution.getVariable(ProcessVariables.RefNoCollabVars.VAR_REFUELING_PROCESS_ID), RefuelingStatus.TERMINATED_ERROR, ProcessVariables.RefNoCollabVars.ERROR_CODE_EXC_ATT);
	}
}
