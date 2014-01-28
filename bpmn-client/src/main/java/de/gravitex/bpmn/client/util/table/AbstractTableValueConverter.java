package de.gravitex.bpmn.client.util.table;


/**
 * Abstrakte Oberklasse für alle ValueConversters.
 * 
 * Ein ValueConversters hat folgende Aufgaben :
 * 
 * - 	Konvertierung von im Datenbereich angezeigten Werten in etwas, das der User sehen will.
 * 		(z.B. Datum : WED 17-08...DEC bla bla nach 17.08.2005).
 * 
 * -	Übersetzung des Inhaltes eines {@link GenericSearchPanel} (a) in Richtung GUI und (b) in Richtung Datenmodell.
 * 		Dieses bezieht sich auf Conboboxen, die die obigen SearchPanels dem Benutzer zur Auswahl von Operatoren oder auch
 * 		Parametern anbieten.
 * 
 *		(a)		aus 'LIKE' wird z.B. 'wie'.
 *
 *				Das Übersetzen von Operatoren hin und zurück passiert mit Hilfe von statischen Methoden in der Klasse
 *				{@link FilterOperators}.
 *
 *		(b)		Das Übersetzen von Parametern ist nötig bei allen Panels, die dem Benutzer die Auswahl eines Parameters
 *				per Combobox vorschreiben. Das ist der Fall bei allen Erben von {@link ParameterBasedSearchPanel}.
 * 
 * @author stefan.schulz
 *
 */
public abstract class AbstractTableValueConverter {	

	/**
	 * Übernimmt das Übersetzen eines Datenwertes für die GUI, z.B. Datum (s.o.).
	 * Diese Methode hat (bisher) keinen Rückweg.
	 * 
	 * @param untranslatedDataValue
	 * @return
	 */
	public abstract String translateDataValueForGUI(Object untranslatedDataValue);
	
	/**
	 * Übernimmt des Parsen eines übergebenen Parameters in die richtige Klassen (Datum als Date usw.).
	 * Die Verantwortlichkeit hierbei liegt beim jew. Erben.
	 * 
	 * @param obj
	 * @return
	 */
	public abstract Object parseParameterValue(Object unparsedValue) throws ValueConverterException;
}
