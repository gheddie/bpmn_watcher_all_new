package de.gravitex.bpmn.server.delegate.refnocollab;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;
import de.gravitex.bpmn.server.commonvalues.ProcessVariables.RefNoCollabVars;

public class InformCustomerDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		final Integer errorCode = (Integer) execution.getVariable(RefNoCollabVars.VAR_ERROR_CODE);
		if (errorCode.equals(ProcessVariables.RefNoCollabVars.ERROR_CODE_NONE))
		{
			System.out.println("amount of '"+execution.getVariable(ProcessVariables.RefNoCollabVars.VAR_AMOUNT_TO_DEBIT)+"' will be charged.");
		} else
		{
			String reason = "";
			switch (errorCode) {
			case ProcessVariables.RefNoCollabVars.ERROR_CODE_EXC_ATT:
				reason = "too much attempts for pin ("+execution.getVariable(RefNoCollabVars.VAR_PIN_ATTEMPTS)+")";
				break;
			case ProcessVariables.RefNoCollabVars.ERROR_CODE_EXC_TIME:
				reason = "exceeded time";
				break;
			case ProcessVariables.RefNoCollabVars.ERROR_CODE_NONE:
				reason = "none";
				break;
			}
			System.out.println("process had to be cancelled for reason : '"+reason+"'.");	
		}
	}
}
