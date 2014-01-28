package de.gravitex.bpmn.client.util.table;

public class FilterOperators {

	public static final FilterOperator[] OPERATORS_NUMERIC = new FilterOperator[] {null, FilterOperator.LESS_THAN, FilterOperator.GREATER_THAN, FilterOperator.EQUALS};

	public static final FilterOperator[] OPERATORS_DATE = new FilterOperator[] {
	                                                                         null,
	                                                                         FilterOperator.EQUALS,
	                                                                         FilterOperator.LESS_THAN,
	                                                                         FilterOperator.LESS_THAN_EQUALS,
	                                                                         FilterOperator.GREATER_THAN,
	                                                                         FilterOperator.GREATER_THAN_EQUALS
	                                                                         };
	
	public static final FilterOperator[] OPERATORS_TEXT = new FilterOperator[] {null, FilterOperator.LIKE, FilterOperator.NOT_LIKE, FilterOperator.EQUALS};
	
	public static final Object[] OPERATORS_BOOLEAN = new Object[] {null, "true", "false"};
	
	//---
	
	private static final Object OPERATOR_EQUALS_TRANSLATED = "genau wie";

	private static final Object OPERATOR_LIKE_TRANSLATED = "wie";

	private static final Object OPERATOR_NOT_LIKE_TRANSLATED = "nicht wie";

	private static final Object OPERATOR_GREATER_THAN_TRANSLATED = "grösser als";

	private static final Object OPERATOR_LESS_THAN_TRANSLATED = "kleiner als";

	private static final Object	OPERATOR_GREATER_THAN_EQUALS_TRANSLATED	= "grösser gleich";

	private static final Object	OPERATOR_LESS_THAN_EQUALS_TRANSLATED	= "kleiner gleich";	
	
	//---
	
	public static Object translateOperatorForGUI(Object operator) {

		if (operator != null) {
			if (operator.equals(FilterOperator.LIKE))
				return OPERATOR_LIKE_TRANSLATED;
			else if (operator.equals(FilterOperator.NOT_LIKE))
				return OPERATOR_NOT_LIKE_TRANSLATED;
			else if (operator.equals(FilterOperator.EQUALS))
				return OPERATOR_EQUALS_TRANSLATED;
			else if (operator.equals(FilterOperator.GREATER_THAN))
				return OPERATOR_GREATER_THAN_TRANSLATED;			
			else if (operator.equals(FilterOperator.LESS_THAN))
				return OPERATOR_LESS_THAN_TRANSLATED;
			
			else if (operator.equals(FilterOperator.GREATER_THAN_EQUALS))
				return OPERATOR_GREATER_THAN_EQUALS_TRANSLATED;
			else if (operator.equals(FilterOperator.LESS_THAN_EQUALS))
				return OPERATOR_LESS_THAN_EQUALS_TRANSLATED;
		}

		return operator;
	}

	public static FilterOperator translateOperatorForDataModel(
			Object untranslatedOperator) {
		
		if (untranslatedOperator != null) {
			
			if (untranslatedOperator.equals(OPERATOR_LIKE_TRANSLATED))
				return FilterOperator.LIKE;
			else if (untranslatedOperator.equals(OPERATOR_NOT_LIKE_TRANSLATED))
				return FilterOperator.NOT_LIKE;
			else if (untranslatedOperator.equals(OPERATOR_EQUALS_TRANSLATED))
				return FilterOperator.EQUALS;
			else if (untranslatedOperator.equals(OPERATOR_GREATER_THAN_TRANSLATED))
				return FilterOperator.GREATER_THAN;			
			else if (untranslatedOperator.equals(OPERATOR_LESS_THAN_TRANSLATED))
				return FilterOperator.LESS_THAN;
			
			else if (untranslatedOperator.equals(OPERATOR_GREATER_THAN_EQUALS_TRANSLATED))
				return FilterOperator.GREATER_THAN_EQUALS;
			else if (untranslatedOperator.equals(OPERATOR_LESS_THAN_EQUALS_TRANSLATED))
				return FilterOperator.LESS_THAN_EQUALS;
		}
		
		return null;
	}	
}
