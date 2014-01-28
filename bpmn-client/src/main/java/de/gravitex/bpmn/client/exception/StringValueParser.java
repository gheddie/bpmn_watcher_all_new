package de.gravitex.bpmn.client.exception;

public class StringValueParser extends AbstractValueParser {

	public Object parse(String textValue) throws VariableParsingException {
		return textValue;
	}
}
