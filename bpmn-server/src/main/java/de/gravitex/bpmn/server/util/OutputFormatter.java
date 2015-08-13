package de.gravitex.bpmn.server.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OutputFormatter
{
	private static final DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
	
	public static String formatDateTime(Date date)
	{
		return df.format(date);
	}
	
	//---
	
	public static void main(String[] args)
	{
		System.out.println(formatDateTime(new Date()));
	}
}
