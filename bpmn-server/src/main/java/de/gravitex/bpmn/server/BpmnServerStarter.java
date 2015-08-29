package de.gravitex.bpmn.server;

import java.rmi.RemoteException;

import de.gravitex.bpmn.server.exception.BpmnException;

public class BpmnServerStarter {

	public static void main(String[] args) {
		try
		{
			ProcessEngineProvider provider = new ProcessEngineProvider();
		} catch (RemoteException e)
		{
			e.printStackTrace();
		} catch (BpmnException e) {
			System.err.println("unable to start : " + e.getMessage());
			System.exit(0);
		}
	}
}
