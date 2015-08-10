package de.gravitex.bpmn.server.delegate.masterquestion;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;

public class ComputeSomethingDelegate implements JavaDelegate
{
	public void execute(DelegateExecution execution) throws Exception
	{
		String s = System.currentTimeMillis() + "";
		switch (s.charAt(s.length()-1)) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			execution.setVariable(ProcessVariables.MasterQuestion.COMPUTED_RESULT, 5);
			break;
		default:
			execution.setVariable(ProcessVariables.MasterQuestion.COMPUTED_RESULT, -5);
			break;
		}
	}
}
