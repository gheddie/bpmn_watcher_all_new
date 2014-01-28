package de.gravitex.bpmn.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BpmnServerStarter {

	public static void main(String[] args) {
		ProcessEngineProvider provider = null;
		Registry registry = null;
		try {
			provider = new ProcessEngineProvider();
			registry = LocateRegistry.createRegistry(RMIConstants.RMI_PORT);
			registry.bind(RMIConstants.RMI_ID, provider);
			System.out.println("process server is started...");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
