package de.gravitex.bpmn.client;

import java.rmi.RemoteException;

import de.gravitex.bpmn.client.singleton.ProcessingSingleton;

public class MessageCorrelationTestClient
{
	public static void main(String[] args)
	{
		try
		{
			ProcessingSingleton.getInstance().correlateMessage("MSG_M2",
					"BK1", null);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
}
