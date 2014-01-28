package de.gravitex.bpmn.client.util.table;


public class TableColumnConfigElement {

	/**
	 * Für das zugehörige Feld existiert nur ein Getter.
	 */
	public static final int				COLUMNTYPE_GETTER_QUALIFIED	= 11;

	/**
	 * Das zugehörige Feld ist vollständig (entity, getter, setter).
	 */
	public static final int				COLUMNTYPE_FULLY_QUALIFIED	= 22;

	private String						entityName					= null;

	private String						mappedColumnName			= null;

	private int							columnType					= COLUMNTYPE_GETTER_QUALIFIED;

	private TableColumnConversionType	conversionType				= TableColumnConversionType.UNDEFINED;

	//alt !!!
	private AbstractTableValueConverter	valueConverter				= null;
	
	//neu !!!
	private AbstractCDBTableColumnValueTranslator valueTranslator = null;

	/** Maximale Spaltenbreite */
	private int							maxWidth				= 0;

	private Class<?> filterCompilerClass;

	private boolean columnEditable = false;

	private boolean searchingEnabled;

	public TableColumnConfigElement(String newEntityName, String newMappedColumnName, boolean newColumnEditable, int newColumnType, TableColumnConversionType newConversionType, AbstractTableValueConverter newValueConverter,
			int newMaxWidth, AbstractCDBTableColumnValueTranslator newValueTranslator, Class<?> newFilterCompilerClass, boolean newSearchingEnabled) {

		super();

		this.entityName = newEntityName;
		this.mappedColumnName = newMappedColumnName;
		this.columnType = newColumnType;
		this.conversionType = newConversionType;

		this.valueConverter = newValueConverter;
		this.maxWidth = newMaxWidth;		
		this.valueTranslator = newValueTranslator;		
		this.filterCompilerClass = newFilterCompilerClass;		
		this.columnEditable = newColumnEditable;
		this.searchingEnabled = newSearchingEnabled;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getMappedColumnName() {
		return mappedColumnName;
	}
	
	public int getColumnType() {
		return columnType;
	}

	public boolean isFullyQualified() {
		return (getColumnType() == COLUMNTYPE_FULLY_QUALIFIED);
	}

	public TableColumnConversionType getConversionType() {
		return this.conversionType;
	}

	public AbstractTableValueConverter getValueConverter() {
		return this.valueConverter;
	}

	
	public String toString() {
		return getClass().getSimpleName() + ", entityname : " + getEntityName();
	}

	public int getMaxWidth() {
		return this.maxWidth;
	}

	public AbstractCDBTableColumnValueTranslator getValueTranslator() {
		return valueTranslator;
	}

	public Class<?> getFilterCompilerClass() {
		return this.filterCompilerClass;
	}

	public boolean isColumnEditable() {
		return this.columnEditable;
	}

	public boolean isSearchingEnabled() {
		return searchingEnabled;
	}
}
