package de.gravitex.bpmn.client.exception;

public class NumericValueParser extends AbstractValueParser
{
	public Object parse(String textValue) throws VariableParsingException
	{
		return Integer.parseInt(textValue);
	}
}
