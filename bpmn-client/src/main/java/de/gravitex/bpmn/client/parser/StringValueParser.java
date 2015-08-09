package de.gravitex.bpmn.client.parser;

import de.gravitex.bpmn.client.exception.VariableParsingException;

public class StringValueParser extends AbstractValueParser {

	public Object parse(String textValue) throws VariableParsingException {
		return textValue;
	}
}
