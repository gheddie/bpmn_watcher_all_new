package de.gravitex.bpmn.client.parser;

import java.util.Calendar;

import de.gravitex.bpmn.client.exception.VariableParsingException;

public class DateValueParser extends AbstractValueParser {

	public Object parse(String textValue) throws VariableParsingException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 2);
		return cal.getTime();
	}
}
