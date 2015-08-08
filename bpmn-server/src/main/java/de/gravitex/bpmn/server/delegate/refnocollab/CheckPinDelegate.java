package de.gravitex.bpmn.server.delegate.refnocollab;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;
import de.gravitex.bpmn.server.commonvalues.ProcessVariables.RefNoCollabVars;

public class CheckPinDelegate implements JavaDelegate
{
	private static final Object PIN = "0815";

	public void execute(DelegateExecution execution) throws Exception
	{
		String pin = (String) execution.getVariable(ProcessVariables.RefNoCollabVars.VAR_PROVIDED_PIN);
		if (pin == null || pin.length() == 0)
		{
			throw new Exception("PIN must not be NULL!!");
		}
		System.out.println("checking PIN : " + pin);
		if (pin.equals(PIN))
		{
			execution.setVariable(ProcessVariables.RefNoCollabVars.VAR_PIN_VALID, true);
			System.out.println("PIN '"+pin+"' is valid...");
		}
		else
		{
			execution.setVariable(ProcessVariables.RefNoCollabVars.VAR_PIN_VALID, false);
			
			//increase attempts
			increaseAttemptCounter(execution);
			
			//reset pin
			execution.setVariable(ProcessVariables.RefNoCollabVars.VAR_PROVIDED_PIN, null);
		}
	}

	private void increaseAttemptCounter(DelegateExecution execution)
	{
		if (execution.getVariable(ProcessVariables.RefNoCollabVars.VAR_PIN_ATTEMPTS) == null)
		{
			execution.setVariable(ProcessVariables.RefNoCollabVars.VAR_PIN_ATTEMPTS, new Integer(1));
			System.out.println("PIN is NOT valid, increasing missed attempt counter to 1.");
		}
		else
		{
			Integer tmp = (Integer) execution.getVariable(RefNoCollabVars.VAR_PIN_ATTEMPTS);
			Integer newAttemptCounter = new Integer(++tmp);
			execution.setVariable(RefNoCollabVars.VAR_PIN_ATTEMPTS, newAttemptCounter);
			System.out.println("PIN is NOT valid, increasing missed attempt counter to "+newAttemptCounter+".");
		}
	}
}
