package de.gravitex.bpmn.client.correlationtest;

import java.rmi.RemoteException;

import de.gravitex.bpmn.client.singleton.ProcessingSingleton;

public class SignalTestClient
{
	public static void main(String[] args)
	{
		try
		{
			ProcessingSingleton.getInstance().triggerSignal("SIG_TRIG_A");
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
}
