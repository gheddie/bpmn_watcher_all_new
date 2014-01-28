package de.gravitex.bpmn.client.util.table;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateValueConverter extends OperatorParameterBasedValueConverter {	
	
	private static final DateFormat	dateFormat	= new SimpleDateFormat("dd.MM.yyyy");

	
	public Object parseParameterValue(Object unparsedValue) throws ValueConverterException {
		
		if (unparsedValue == null)
			return null;
		
		String tmpStringVal = String.valueOf(unparsedValue);
		String[] str = tmpStringVal.split("\\.");
		
		int tmpDay = -1;
		int tmpMonth = -1;
		int tmpYear = -1;
		
		if ((str != null) && (str.length == 3)) {
			try {
				tmpDay = Integer.parseInt(str[0].trim());
				tmpMonth = Integer.parseInt(str[1].trim());
				tmpYear = Integer.parseInt(str[2].trim());					
			} catch (NumberFormatException exc) {
				//...
			}	
		}	
		
		 if(!(DateOperations.isDateCorrectMsSqlSpecific(tmpDay, tmpMonth, tmpYear))) {
				return null;
		 }		
		 
		 /**
		  * Jetzt ist ein korrektes Datum...
		  */
		 Date date = toDate(tmpStringVal);
		
		return date;
	}
	
	private Date toDate(String text) {
		// logger.debug("converting to java.util.date : " + text);
		try {
			return dateFormat.parse(text);
		} catch (ParseException e) {
			return null;
		}
	}	
	
	
	public String translateDataValueForGUI(Object untranslatedGUIValue) {
		if(untranslatedGUIValue instanceof Date) {
			return ValueFormatter.formatDate((Date) untranslatedGUIValue);
		}
		else if(untranslatedGUIValue instanceof Calendar) {
			return ValueFormatter.formatDate(((Calendar) untranslatedGUIValue).getTime());
		}
		else {
			return "";
		}
	}
}
