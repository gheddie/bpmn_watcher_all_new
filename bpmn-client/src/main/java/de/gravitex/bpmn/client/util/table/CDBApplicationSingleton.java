package de.gravitex.bpmn.client.util.table;

public class CDBApplicationSingleton {

	private static CDBApplicationSingleton instance;

	public static Object translateEnumValue(Object value) {
		return getInstance().getEnumValueTranslator().translate(value);
	}

	private EnumValueTranslator enumValueTranslator;

	private EnumValueTranslator getEnumValueTranslator() {
		return this.enumValueTranslator;
	}	

	private static CDBApplicationSingleton getInstance() {
		if (instance == null) {
			instance = new CDBApplicationSingleton();
		}
		return instance;
	}

	public static Object retranslateEnumValue(Object value) {
		return getInstance().getEnumValueTranslator().retranslate(value);
	}
}
