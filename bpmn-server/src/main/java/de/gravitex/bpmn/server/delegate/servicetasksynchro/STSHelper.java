package de.gravitex.bpmn.server.delegate.servicetasksynchro;

public class STSHelper
{
	public static void countToInfinity(String whoIsIt)
	{
		System.out.println(" *** " + whoIsIt + " ...counts to infinity (START)...");
		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		/*
		for (int i=0;i<10000;i++)
		{
			for (int j=0;j<10000;j++)
			{
				//...
			}
		}
		*/
		System.out.println(" *** " + whoIsIt + " ...counts to infinity (END)...");
	}
}
