package de.gravitex.bpmn.client.exception;

public abstract class AbstractValueParser {

	public abstract Object parse(String textValue) throws VariableParsingException;
}
