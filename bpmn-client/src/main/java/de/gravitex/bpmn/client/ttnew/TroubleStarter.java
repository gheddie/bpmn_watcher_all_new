package de.gravitex.bpmn.client.ttnew;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.MismatchingMessageCorrelationException;

import de.gravitex.bpmn.client.singleton.ProcessingSingleton;
import de.gravitex.bpmn.server.commonvalues.ProcessVariables;
import de.gravitex.bpmn.server.processhelper.troubleticket.TroubleTicketHelper;

public class TroubleStarter
{
	private static final String TASK_ID_SOLVE_TICKET = "TaskSolveTicket";

	public static void main(String[] args)
	{
		startProcess();
		
//		changeResponsibility();
		
//		reopenTicket();
	}

	private static void reopenTicket()
	{
		try
		{
			ProcessingSingleton.getInstance().correlateMessage("MSG_REOPEN_TICKET",
					null, null);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	private static void changeResponsibility()
	{
		try
		{
//			List<Task> taskList = ProcessingSingleton.getInstance().queryTasks("TT_1234_1440703452033", TASK_ID_SOLVE_TICKET);
			ProcessingSingleton.getInstance().correlateMessage("MSG_CHANGE_RESP",
					null, null);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		} catch (MismatchingMessageCorrelationException e) {
			// TODO: handle exception
		}
	}

	private static void startProcess()
	{
		try
		{
			String generatedTicketNumber = TroubleTicketHelper.generateTicketNumber();
			String ttBusinessKey = "TT_" + generatedTicketNumber + "_" + System.currentTimeMillis();
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put(ProcessVariables.TroubleTicketNew.VAR_ESCALATION_LEVEL, new Integer(0));
			//start process
			ProcessingSingleton.getInstance().correlateMessage("MSG_TROUBLE_ANNOUNCED",
					ttBusinessKey, variables);
			//start escalation
			ProcessingSingleton.getInstance().correlateMessage("MSG_START_ESCALATION",
					ttBusinessKey, null);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
}