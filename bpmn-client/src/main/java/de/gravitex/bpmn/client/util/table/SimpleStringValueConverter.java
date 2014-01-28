package de.gravitex.bpmn.client.util.table;

public class SimpleStringValueConverter extends OperatorParameterBasedValueConverter {

		
	public Object parseParameterValue(Object unparsedValue) {		
		return String.valueOf(unparsedValue);
	}

	
	public String translateDataValueForGUI(Object untranslatedGUIValue) {
		if (untranslatedGUIValue == null)
			return "";
		return String.valueOf(untranslatedGUIValue);
	}
}
