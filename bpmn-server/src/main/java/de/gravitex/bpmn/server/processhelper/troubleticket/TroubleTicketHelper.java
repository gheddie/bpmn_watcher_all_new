package de.gravitex.bpmn.server.processhelper.troubleticket;

import java.util.Calendar;
import java.util.Date;

public class TroubleTicketHelper
{
	public static String generateTicketNumber()
	{
		return "1234";
	}

	public static Date calculateNextEscalationTime(Integer escalationLevel)
	{
		Calendar calendar = Calendar.getInstance();
		switch (escalationLevel) {
		case 0:
			//------------------------------------------------------
			calendar.add(Calendar.MINUTE, 2);
			return calendar.getTime();
			//------------------------------------------------------
		case 1:
			//------------------------------------------------------
			calendar.add(Calendar.MINUTE, 3);
			return calendar.getTime();
			//------------------------------------------------------
		default:
			//------------------------------------------------------
			calendar.add(Calendar.MINUTE, 4);
			return calendar.getTime();
			//------------------------------------------------------
		}
	}
}