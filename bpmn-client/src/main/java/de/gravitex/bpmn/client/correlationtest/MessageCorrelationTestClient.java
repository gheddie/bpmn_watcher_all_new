package de.gravitex.bpmn.client.correlationtest;

import java.rmi.RemoteException;

import de.gravitex.bpmn.client.singleton.ProcessingSingleton;

public class MessageCorrelationTestClient
{
	public static void main(String[] args)
	{
		try
		{
			ProcessingSingleton.getInstance().correlateMessage("MSG_CLOSE_TICKET",
					"bk35", null);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
}
