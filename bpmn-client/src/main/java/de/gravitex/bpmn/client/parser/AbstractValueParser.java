package de.gravitex.bpmn.client.parser;

import de.gravitex.bpmn.client.exception.VariableParsingException;

public abstract class AbstractValueParser {

	public abstract Object parse(String textValue) throws VariableParsingException;
}
