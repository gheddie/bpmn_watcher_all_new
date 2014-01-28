package de.gravitex.bpmn.client.exception;

import java.util.Calendar;

public class DateValueParser extends AbstractValueParser {

	public Object parse(String textValue) throws VariableParsingException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 2);
		return cal.getTime();
	}
}
