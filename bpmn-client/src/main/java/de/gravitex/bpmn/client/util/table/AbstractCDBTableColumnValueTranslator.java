package de.gravitex.bpmn.client.util.table;


public abstract class AbstractCDBTableColumnValueTranslator {
	
	/**
	 * Enthält die Übersetzungsinformationen (KEY=untranslated, VAL=translated).
	 * Der spezielle Datentyp ermöglichkeit die Zugänglichkeit von beiden Seiten.
	 */
	private InvertableHashMap translationMap = null;
	
	public AbstractCDBTableColumnValueTranslator() {
		super();
		putTranslationPairs();
	}

	/**
	 * Hier müssen die nötigen Übersetzungsinformationen
	 * (f.d. jew. Erben) abgelegt werden.
	 */
	protected abstract void putTranslationPairs();

	public Object[] translateAllValues() {
		Object[] _translated = new Object[getUntranslatedData().length];
		int _index = 0;
		for (Object _untranslated : getUntranslatedData()) {
			_translated[_index++] = translateParameterForGUI(_untranslated);
		}		
		return _translated;
	}

	protected abstract Object[] getUntranslatedData();

	/**
	 * Übersetzt den (f.d. GUI aufbereiteten) Wert 'guiTranslatedValue' in die
	 * für das Datenmodell verständlichen Form zurück.
	 * 
	 * @param guiTranslatedValue	der f.d. GUI aufbereitete Wert
	 * @return						der f.d. Datenmodell verständliche Wert
	 */
	public Object translateParameterForDateModel(Object guiTranslatedValue) {
		return getTranslationMap().searchByValue(guiTranslatedValue);
	}
	
	protected Object translateParameterForGUI(Object dataModelValue) {
		Object _guiTranslateParameter = getTranslationMap().searchByKey(dataModelValue);
		if (_guiTranslateParameter == null) {
			/**
			 * Wird keine Übersetzung gefunden, so wird
			 * der unübersetzte Wert zurückgegeben...
			 */
			return dataModelValue;	
		}	
		return _guiTranslateParameter;
	}	
	
	protected void addTranslationPair(Object untranslatedValue, Object translatedValue) {
		getTranslationMap().putValuePair(untranslatedValue, translatedValue);
	}

	private InvertableHashMap getTranslationMap() {
		if (this.translationMap == null)
			this.translationMap = new InvertableHashMap();
		return this.translationMap;
	}

	public Object translateSingleValue(Object value) {
		return getTranslationMap().searchByKey(value);
	}
	
	protected void addUntranslated(Object object) {
		getTranslationMap().putValuePair(object, object);
	}
}
