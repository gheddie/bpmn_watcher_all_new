package de.gravitex.bpmn.client.util.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TableColumnConfig {
	
	private List<TableColumnConfigElement> configElements = null;

	/**
	 * Die Klasse der Beans, die im zugehörigen Table dargestellt werden sollen.
	 */
	private Class<?> entityClass = null;

	public TableColumnConfig(Class<?> newEntityClass) {
		super();
		this.entityClass = newEntityClass;
	}

	public void addConfigElement(String newGetterMethodName, String newMappedColumnName, boolean newColumnEditable, int newColumnType, TableColumnConversionType newConversionType,
			AbstractTableValueConverter newValueConverter, int newMaxWidth, Class<?> newEntityClass, boolean newSearchingEnabled) {

		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, newConversionType, newValueConverter, newMaxWidth, null, newEntityClass, newSearchingEnabled));
	}

	/**
	 * Ohne bevorzugte Spalten-Breite
	 * 
	 * @param newGetterMethodName
	 * @param newMappedColumnName
	 * @param newColumnEditable
	 * @param newColumnType
	 * @param newConversionType
	 * @param ________________oldValueConverter
	 * @param newSearchingEnabled 
	 */
	public void addConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, TableColumnConversionType newConversionType,
			AbstractTableValueConverter ________________oldValueConverter, boolean newSearchingEnabled) {

		addConfigElement(newGetterMethodName, newMappedColumnName, false, newColumnType, newConversionType, ________________oldValueConverter, -1, null, newSearchingEnabled);
	}

	public List<TableColumnConfigElement> getConfigElements() {
		if (this.configElements == null)
			this.configElements = new ArrayList<TableColumnConfigElement>();
		return this.configElements;
	}

	public TableColumnConfigElement getConfigElementByUnmappedName(String fieldName) {
		for (TableColumnConfigElement element : getConfigElements())
			if (fieldName.equals(element.getEntityName()))
				return element;
		return null;
	}

	public int getElementCount() {
		return getConfigElements().size();
	}

	public Vector<String> getColumnNames() {

		ArrayList<String> _columnNames = new ArrayList<String>();

		for (TableColumnConfigElement _element : getConfigElements()) {
			_columnNames.add(_element.getMappedColumnName());
		}
		return new Vector<String>(_columnNames);
	}

	public Class<?> getEntityClass() {
		return this.entityClass;
	}

	public TableColumnConfigElement getConfigElementAt(int index) {
		return getConfigElements().get(index);
	}

	// ---

	public void addTextConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable, boolean newSearchingEnabled) {
		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, TableColumnConversionType.TEXT, new SimpleStringValueConverter(), -1, null, null, newSearchingEnabled));
	}
	
	public void addPlainTextConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable) {
		addPlainTextConfigElement(newGetterMethodName, newMappedColumnName, newColumnType, newColumnEditable, true);
	}

	public void addPlainTextConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable, boolean newSearchingEnabled) {
		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, TableColumnConversionType.TEXT_PLAIN, null, -1, null, null, newSearchingEnabled));
	}
	
	public void addScaleConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable, boolean newSearchingEnabled) {
		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, TableColumnConversionType.TEXT_PLAIN, null, -1, null, null, newSearchingEnabled));
	}
	
	public void addDateTimeConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable, boolean newSearchingEnabled) {
		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, TableColumnConversionType.DATE, new DateTimeValueConverter(), -1, null, null, newSearchingEnabled));
	}
	
	/**
	 * Works for an calendar-object as good as for a date-object.
	 * @param newGetterMethodName
	 * @param newMappedColumnName
	 * @param newColumnType
	 * @param newColumnEditable
	 */
	public void addDateConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable) {
		addDateConfigElement(newGetterMethodName, newMappedColumnName, newColumnType, newColumnEditable, true);
	}

	public void addDateConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable, boolean newSearchingEnabled) {
		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, TableColumnConversionType.DATE, new DateValueConverter(), -1, null, null, newSearchingEnabled));
	}

	public void addNumericConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable, boolean newSearchingEnabled) {
		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, TableColumnConversionType.NUMERIC, null, -1, null, null, newSearchingEnabled));
	}
	
	public void addNumericPlainConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable) {
		addNumericPlainConfigElement(newGetterMethodName, newMappedColumnName, newColumnType, newColumnEditable, true);
	}
	
	public void addNumericPlainConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable, AbstractTableValueConverter tableValueConverter) {
		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, TableColumnConversionType.NUMERIC_PLAIN, tableValueConverter, -1, null, null, false));
	}

	public void addNumericPlainConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable, boolean newSearchingEnabled) {
		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, TableColumnConversionType.NUMERIC_PLAIN, null, -1, null, null, newSearchingEnabled));
	}	
	
	public void addBooleanConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, boolean newColumnEditable, boolean newSearchingEnabled) {
		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, TableColumnConversionType.BOOLEAN, null, -1, null, null, newSearchingEnabled));
	}
	
	public void addEnumConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, AbstractCDBTableColumnEnumValueTranslator newEnumParameterTranslator, Class<?> newEntityClass, boolean newColumnEditable) {
		addEnumConfigElement(newGetterMethodName, newMappedColumnName, newColumnType, newEnumParameterTranslator, newEntityClass, newColumnEditable, true);
	}

	public void addEnumConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, AbstractCDBTableColumnEnumValueTranslator newEnumParameterTranslator, Class<?> newEntityClass, boolean newColumnEditable, boolean newSearchingEnabled) {
		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, TableColumnConversionType.ENUM, null, -1, newEnumParameterTranslator, newEntityClass, newSearchingEnabled));
	}
	
	public void addConstantValueConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, AbstractCDBTableColumnValueConstantValueTranslator valueTranslator, boolean newColumnEditable) {
		addConstantValueConfigElement(newGetterMethodName, newMappedColumnName, newColumnType, valueTranslator, newColumnEditable, true);
	}

	public void addConstantValueConfigElement(String newGetterMethodName, String newMappedColumnName, int newColumnType, AbstractCDBTableColumnValueConstantValueTranslator valueTranslator, boolean newColumnEditable, boolean newSearchingEnabled) {
		getConfigElements().add(new TableColumnConfigElement(newGetterMethodName, newMappedColumnName, newColumnEditable, newColumnType, TableColumnConversionType.CONSTANTVALUES, null, -1, valueTranslator, null, newSearchingEnabled));
	}
}
