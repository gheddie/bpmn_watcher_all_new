package de.gravitex.bpmn.client.correlationtest;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import de.gravitex.bpmn.client.singleton.ProcessingSingleton;

public class MessageCorrelationTestClient
{
	public static void main(String[] args)
	{
		try
		{
			// --------------------------------------------- start process
			Map<String, Object> variables = new HashMap<>();
			variables.put("libraryName", "someCoolLibrary");
			ProcessingSingleton.getInstance().correlateMessage("MSG_LIB_REQ",
					null, variables);
			// --------------------------------------------- available
//			HashMap<String, Object> variables = new HashMap<String, Object>();
//			ProcessingSingleton.getInstance().correlateMessage("MSG_LIC_AVAIL",
//					null, variables);
			// --------------------------------------------- intermediate test
//			ProcessingSingleton.getInstance().correlateMessage("MSG_PTT_1",
//			"bk54", null);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
}
