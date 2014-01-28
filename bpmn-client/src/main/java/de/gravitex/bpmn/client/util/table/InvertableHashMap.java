package de.gravitex.bpmn.client.util.table;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * Ein Container f�r eine HashMap.
 * Sie bietet neben der normalen Funktionalit�t
 * (Suche nach einem Wert mit einem Schl�ssel)
 * auch an, den umgekehrten Weg zu beschreiten
 * um mit einem Wert nach seinem Schl�ssel zu suchen.
 * 
 * @author stefan.schulz
 *
 */
public class InvertableHashMap {
	
	private static Logger			logger					= Logger.getLogger(InvertableHashMap.class);
	
	private HashMap<Object, Object> containedMap = null;
	
	/**
	 * Dient zur Erhöhung der Performance. Wurde bei der Suche nach einem Wert
	 * {@link InvertableHashMap#searchByValue(Object)} der dazugrhörige Key
	 * gefunden, so wird dieses Paar hier vertauscht gespeichert (Key als Wert und
	 * andersherum).
	 */
	private HashMap<Object, Object> invertedKeyValuePairs = null;
	
	public InvertableHashMap() {
		super();
		this.containedMap = new HashMap<Object, Object>();
	}

	public InvertableHashMap(HashMap<Object, Object> newContainedMap) {
		super();
		this.containedMap = newContainedMap;
	}

	private HashMap<Object, Object> getContainedMap() {
		return this.containedMap;
	}
	
	/**
	 * Normale HashMap - Funktionalit�t
	 * 
	 * @param key
	 * @return
	 */
	public Object searchByKey(Object key) {
		return getContainedMap().get(key);
	}

	/**
	 * Suche nach einem Schl�ssel mit einem Wert.
	 * 
	 * @param value
	 * @return
	 */
	public Object searchByValue(Object value) {
		
		logger.trace("searching by value : " + value);
		
		/**
		 * Wurde schon einmal (erfolgreich) nach diesem
		 * Wert gesucht ? Dann ist das schon gespeichert...
		 */
		if (getInvertedKeyValuePairs().get(value) != null)
			return getInvertedKeyValuePairs().get(value);
		
		for (Object key : getContainedMap().keySet())
			if (getContainedMap().get(key).equals(value)) {
				//Für das nöchste Mal merken...
				getInvertedKeyValuePairs().put(value, key);
				return key;	
			}				
		return null;
	}

	private HashMap<Object, Object> getInvertedKeyValuePairs() {
		if (this.invertedKeyValuePairs == null)
			this.invertedKeyValuePairs = new HashMap<Object, Object>();
		return this.invertedKeyValuePairs;
	}
	
	public void putValuePair(Object key, Object value) {
		getContainedMap().put(key, value);
	}
}
