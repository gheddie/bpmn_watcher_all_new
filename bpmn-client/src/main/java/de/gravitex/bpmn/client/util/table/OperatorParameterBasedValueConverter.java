package de.gravitex.bpmn.client.util.table;


public abstract class OperatorParameterBasedValueConverter extends AbstractTableValueConverter {
	
	/**
	 * Übersetzt z.B. 'LIKE' in 'wie'. 
	 * @param unconvertedOperator 
	 * 
	 * @return
	 */
	public Object convertOperatorforGUI(FilterOperator unconvertedOperator) {
		return FilterOperators.translateOperatorForGUI(unconvertedOperator);
	}	

	/**
	 * Übersetzt z.B. 'wie' in 'LIKE'.
	 * Rückgabewert ist eine Instanz von {@link FilterOperator}. 
	 * @param convertedOperator 
	 * 
	 * @return
	 */	
	public FilterOperator convertOperatorforDataModel(Object convertedOperator) {
		return FilterOperators.translateOperatorForDataModel(convertedOperator);
	}
	
	/**
	 * Übersetzt mehrere übergebene unübersetze
	 * Operatoren auf einen Schlag.
	 * 
	 * @return
	 */
	public Object[] convertOperatorsforGUI(FilterOperator[] unconvertedOperators) {
		Object[] convertedOperators = new Object[unconvertedOperators.length];
		int index = 0;
		for (FilterOperator tmpOperator : unconvertedOperators) {			
			convertedOperators[index] = convertOperatorforGUI(unconvertedOperators[index]);
			index++;
		}		
		return convertedOperators;
	}
}
