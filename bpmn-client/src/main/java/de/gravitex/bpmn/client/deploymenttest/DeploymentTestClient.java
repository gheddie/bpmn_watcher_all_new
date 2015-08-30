package de.gravitex.bpmn.client.deploymenttest;

import java.rmi.RemoteException;

import de.gravitex.bpmn.client.singleton.ProcessingSingleton;
import de.gravitex.bpmn.server.exception.BpmnException;

public class DeploymentTestClient
{
	public static void main(String[] args)
	{
		try
		{
			ProcessingSingleton.getInstance().deploy("CompensationTest", true);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		} catch (BpmnException e) {
			System.err.println("unable to deploy : " + e.getMessage());
			System.exit(0);
		}
	}
}
