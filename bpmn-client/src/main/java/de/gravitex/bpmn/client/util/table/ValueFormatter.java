package de.gravitex.bpmn.client.util.table;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValueFormatter {
	
	private static final DateFormat	dateFormat	= new SimpleDateFormat("dd.MM.yyyy");
	
	private static final DateFormat	dateTimeFormat	= new SimpleDateFormat("dd.MM.yyyy HH:mm");

	public static String formatDateTime(Date qualifiedDateTime) {
		if (qualifiedDateTime != null)
			return dateTimeFormat.format(qualifiedDateTime);
		return "";
	}

	public static String formatDate(Date qualifiedDate) {
		if (qualifiedDate != null)
			return dateFormat.format(qualifiedDate);
		return "";
	}
}
