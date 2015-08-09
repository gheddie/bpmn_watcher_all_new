package de.gravitex.bpmn.client.parser;

import java.util.HashMap;

import de.gravitex.bpmn.client.VariablesType;
import de.gravitex.bpmn.client.exception.VariableParsingException;

public class VariableParser {
	
	private static HashMap<VariablesType, AbstractValueParser> parsers = new HashMap<>();
    static {
            parsers.put(VariablesType.BOOLEAN, new BooleanValueParser());
            parsers.put(VariablesType.STRING, new StringValueParser());
            parsers.put(VariablesType.DATE, new DateValueParser());
            parsers.put(VariablesType.NUMERIC, new NumericValueParser());
    }

	public static Object parseValue(VariablesType type, String textValue) throws VariableParsingException {
		return parsers.get(type).parse(textValue);
	}
}
