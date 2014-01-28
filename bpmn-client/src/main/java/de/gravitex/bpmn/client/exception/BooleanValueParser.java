package de.gravitex.bpmn.client.exception;

public class BooleanValueParser extends AbstractValueParser {

	public Object parse(String textValue) throws VariableParsingException {
		if ((textValue == null) || (textValue.length() == 0)) {
			throw new VariableParsingException("boolean value can not be parsed from empty string.");
		}
		textValue = textValue.toUpperCase();
		if (textValue.equals("TRUE")) {
			return Boolean.TRUE;
		} else if (textValue.equals("FALSE")) {
			return Boolean.FALSE;
		} else {
			throw new VariableParsingException("boolean value can not be parsed from string '"+textValue+"'.");
		}
	}
}
