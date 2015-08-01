package de.gravitex.bpmn.server.delegate.refnocollab;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class InformCustomerDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		final Integer errorCode = (Integer) execution.getVariable(RefNoCollabVariables.VAR_ERROR_CODE);
		if (errorCode != null)
		{
			String reason = "";
			switch (errorCode) {
			case RefNoCollabVariables.ERROR_CODE_EXC_ATT:
				reason = "too much attempts for pin ("+execution.getVariable(RefNoCollabVariables.VAR_PIN_ATTEMPTS)+")";
				break;
			case RefNoCollabVariables.ERROR_CODE_EXC_TIME:
				reason = "exceeded time";
				break;
			}
			System.out.println("process had to be cancelled for reason : '"+reason+"'.");
		}
		else
		{
			System.out.println("process had to be cancelled (no error code provided).");
		}
	}
}
