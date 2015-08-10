package de.gravitex.bpmn.client.deploymenttest;

import java.rmi.RemoteException;

import de.gravitex.bpmn.client.singleton.ProcessingSingleton;

public class DeploymentTestClient
{
	public static void main(String[] args)
	{
		try
		{
			ProcessingSingleton.getInstance().deploy("VerySimpleProcess", true);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
}
