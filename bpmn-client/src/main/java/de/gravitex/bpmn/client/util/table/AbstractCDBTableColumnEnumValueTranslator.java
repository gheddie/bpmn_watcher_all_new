package de.gravitex.bpmn.client.util.table;


public abstract class AbstractCDBTableColumnEnumValueTranslator extends AbstractCDBTableColumnValueTranslator {

	public AbstractCDBTableColumnEnumValueTranslator() {
		super();
	}
	
	/**
	 * Das Übersetzen von Enum-Werten wird durch
	 * {@link CDBApplicationSingleton#translateEnumValue(Object)}
	 * übernommen.
	 * 
	 */
	
	protected Object translateParameterForGUI(Object dataModelValue) {
		return CDBApplicationSingleton.translateEnumValue(dataModelValue);
	}
	
	/**
	 * Das Rückübersetzen von Enum-Werten wird durch
	 * {@link CDBApplicationSingleton#retranslateEnumValue(Object)}
	 * übernommen.
	 * 
	 */	
	
	public Object translateParameterForDateModel(Object guiTranslatedValue) {
		return CDBApplicationSingleton.retranslateEnumValue(guiTranslatedValue);
	}
	
	/**
	 * Hier muss dafür gesorgt werden, dass nicht
	 * ({@link AbstractCDBTableColumnValueTranslator#translateSingleValue(Object)}),
	 * sondern {@link CDBApplicationSingleton#translateEnumValue(Object)} benutzt wird,
	 * weil ja die Erben dieser Klasse auch keine Eintragungen über
	 * {@link AbstractCDBTableColumnValueTranslator#addTranslationPair(Object, Object)}
	 * vornehmen. Die Übersetzungen f.d. gewünschten Enum-Werte können also dort
	 * auch nicht gefunden werden.
	 */
	
	public Object translateSingleValue(Object value) {
		return CDBApplicationSingleton.translateEnumValue(value);
	}
}
