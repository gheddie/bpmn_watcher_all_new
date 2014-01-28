package de.gravitex.bpmn.client.util.table;

import java.io.Serializable;

public enum FilterOperator implements Serializable {
	EQUALS,
	NOT_EQUALS,
	LIKE,
	NOT_LIKE,
	GREATER_THAN,
	GREATER_THAN_EQUALS,
	LESS_THAN,
	LESS_THAN_EQUALS
}
