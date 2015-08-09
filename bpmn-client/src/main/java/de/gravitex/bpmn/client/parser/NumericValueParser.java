package de.gravitex.bpmn.client.parser;

import de.gravitex.bpmn.client.exception.VariableParsingException;

public class NumericValueParser extends AbstractValueParser
{
	public Object parse(String textValue) throws VariableParsingException
	{
		return Integer.parseInt(textValue);
	}
}
