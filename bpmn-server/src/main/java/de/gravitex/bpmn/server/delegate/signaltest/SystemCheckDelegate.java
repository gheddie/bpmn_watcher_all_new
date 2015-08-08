package de.gravitex.bpmn.server.delegate.signaltest;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;

public class SystemCheckDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		int numFaults = new Random().nextInt(5);
		boolean exceeded = numFaults >= 3;
		execution.setVariable(ProcessVariables.SignalTestVars.VAR_FAULTS_EXCEEDED, exceeded);
		if (exceeded)
		{
			System.out.println("SystemCheckDelegate : detected "+numFaults+" fault (too much, sorry)...");	
		}
		else
		{
			System.out.println("SystemCheckDelegate : detected "+numFaults+" fault (fairly OK)...");
		}
	}
}
