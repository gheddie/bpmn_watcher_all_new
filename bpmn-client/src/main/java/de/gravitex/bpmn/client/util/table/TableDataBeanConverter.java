package de.gravitex.bpmn.client.util.table;

import java.util.HashSet;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

public class TableDataBeanConverter {

	private static Logger		logger				= Logger.getLogger(TableDataBeanConverter.class);
	
	private static final String UNTRANSLATED_ENUM_OR_CONSTANT_VALUE = "";

	private Object[][] convertedData = null;
	
	private HashSet<Integer> alreadyConvertedIndicies = null;

	private Object[] headers = null;
	
	private List<?> rawData = null;
	
	private Object leadingBean = null;
	
	private TableColumnConfig columnConfig = null;

	public TableDataBeanConverter(List<?> newRawData, TableColumnConfig newColumnConfig) {
		super();
		
		this.rawData = newRawData;
		this.columnConfig = newColumnConfig;
		
		if ((getRawData() != null) && (getRawData().size() > 0))
			setLeadingBean(getRawData().get(0));			
	}

	public DefaultTableModel buildModel() throws MvcGuiComponentInitializationIncompleteException {		

		Object[] tmpHeaders = buildHeaders();

		setConvertedData(buildEmptyData());			
		
		for (int row=0;row<rawData.size();row++) {
			convertedDataAtIndex(row);
		}
		
		DefaultTableModel _model = new DefaultTableModel(getConvertedData(), tmpHeaders);
		
		return _model;
	}

	/**
	 * Baut ein zweidimensionalen Vektor auf,
	 * in welchen dann später nach und nach
	 * die konvierten Daten eingefügt werden.
	 * Vorerst sind alle Felder darin [NULL].
	 * 
	 * @return
	 */
	protected Object[][] buildEmptyData() {
		logger.trace("build empty data (size:"+getDatasetCount()+")");
		return new Object[getDatasetCount()][getColumnConfig().getElementCount()];
	}

	protected Object[] harvestDataEntity(Object bean) {

		Object[] data = new Object[getColumnConfig().getElementCount()];
		
		int index = 0;
		for (TableColumnConfigElement element : getColumnConfig().getConfigElements()) {

			/**
			 * Wenn es sich um ein voll qualifiziertes Element handelt, dan kann
			 * die Methode 'buildGetterName' selbstständig entscheiden, ob is...
			 * oder get... aufzurufen, weil es den Typ des dahinterliegenden
			 * Members abfragen kann. Wenn es sich aber um ein Element handelt,
			 * des nur aus einem getter besteht, ist hier raten angesagt...
			 */

			Object value = null;

			try {
				if (element.isFullyQualified()) {
					// (1.a) check if the entityname contains a dot --> getter must be called from another object
					if (element.getEntityName().contains(".")) {
						// (2) split the entityname
						String[] elems = element.getEntityName().split("\\.");
						Object tmpBean = bean;
						// (3) save the "real" getter method in the tmpValue
						String realGetter = elems[elems.length-1];
						// (4) walk over elements before the real getter
						for (int i = 0; i < elems.length - 1; i++) {	
							// (5) create the bean-getter method-name
							String getterName = ReflectionHelperMethods.buildGetterName(bean, elems[i]);
							// (6) invoke the bean-getter and save it as new bean
							tmpBean = ReflectionHelperMethods.invokeGetter(tmpBean, getterName);
							// TODO KK : is it possible to use ObjectRefMgr here?
//							tmpBean = ObjectReferenceManager.getMemberReference((BasicPkEntity) tmpBean, realGetter);
						}					

						// (7) the returned bean may be null --> check this and return an empty string in that case.
						if (tmpBean != null) {
							// (8) if its not null, we call the real getter method of the bean that has been finally saved.
							value = ReflectionHelperMethods.invokeGetter(tmpBean, ReflectionHelperMethods.buildGetterName(tmpBean, realGetter));
						}
						else {
							value = "";
						}
					}		
					// (1.b) if the entityname does not contain any dots, we simply invoke the getter method to obtain the value
					else {
						value = ReflectionHelperMethods.invokeGetter(bean, ReflectionHelperMethods.buildGetterName(bean, element.getEntityName()));
					}
					
				} else {
					value = ReflectionHelperMethods.invokeGetter(bean, ReflectionHelperMethods.guessGetterName(bean, element.getEntityName()));
				}
			} catch (NoSuchMethodException e) {
				logger.error(e);
			}

			/**
			 * Hier muss f.d. Übergang eine Sonderbehandlung erfolgen...
			 */
			AbstractTableValueConverter tmpConverter = element.getValueConverter();			
			
			if (
					(element.getConversionType().equals(TableColumnConversionType.ENUM))
					||
					(element.getConversionType().equals(TableColumnConversionType.CONSTANTVALUES))
					) {
				Object translatedValue = element.getValueTranslator().translateSingleValue(value);
				if (translatedValue == null)
					data[index] = UNTRANSLATED_ENUM_OR_CONSTANT_VALUE;
				else
					data[index] = translatedValue;
			} else {
				/**
				 * Hier wird der Wert ggf. durch den hinterlegten ValueConverter
				 * übersetzt, wenn in seiner 'translateForGUI()'- Methode eine
				 * Entsprechung gefunden wird.
				 * 
				 */
				if (tmpConverter != null)
					data[index] = tmpConverter.translateDataValueForGUI(value);
				else
					data[index] = value;	
			}
			
			index++;
		}

		return data;
	}

	/**
	 * Diese Methode muss ein LAZY-Verhalten ausweisen,
	 * weil sie u.a. jedesmal betreten wird, wenn ein neuer
	 * Datenbereich gerendert wird. Daher wird hier erst
	 * gecheckt, ob das Bauen der Headers schon passiert ist.
	 * @throws MvcGuiComponentInitializationIncompleteException 
	 */
	protected Object[] buildHeaders() throws MvcGuiComponentInitializationIncompleteException {
		
		if (this.headers  == null) {
			if (getColumnConfig() == null)
				throw new MvcGuiComponentInitializationIncompleteException("column config must not be null !!");
			
			Object[] tmpHeaders = new Object[getColumnConfig().getElementCount()];
			int index = 0;
			for (TableColumnConfigElement element : getColumnConfig().getConfigElements()) {			
				if (element.getMappedColumnName() != null) {
					tmpHeaders[index] = element.getMappedColumnName();
				} else {
					tmpHeaders[index] = element.getEntityName();	
				}				
				index++;
			}
			this.headers = tmpHeaders;
		}		
		return this.headers;
	}

	protected Object[][] getConvertedData() {
		return this.convertedData;
	}

	private void setConvertedData(Object[][] newConvertedData) {
		this.convertedData = newConvertedData;
	}

	public void convertDatasetAt(int rowIndex) {			
		
		if (!isDataConvertedAtIndex(rowIndex)) {
			convertedDataAtIndex(rowIndex);
		}
	}

	protected void convertedDataAtIndex(int rowIndex) {
		
		if (!(rowIndex >= 0))
			return;
		
		Object[] dataEntity = harvestDataEntity(getRawData().get(rowIndex));
		
		for (int colIndex=0;colIndex<getColumnConfig().getElementCount();colIndex++) {
			Object object = dataEntity[colIndex];
			getConvertedData()[rowIndex][colIndex] = object;	
		}			
					
		/**
		 * Jetzt wurde sie konvertiert...
		 */
		getAlreadyConvertedIndicies().add(new Integer(rowIndex));
	}

	private boolean isDataConvertedAtIndex(int index) {
		return getAlreadyConvertedIndicies().contains(new Integer(index));
	}

	protected HashSet<Integer> getAlreadyConvertedIndicies() {
		if (this.alreadyConvertedIndicies == null)
			this.alreadyConvertedIndicies = new HashSet<Integer>();
		return this.alreadyConvertedIndicies;
	}

	/**
	 * Löscht den Cache, in dem gepsichert wird, welche Beans
	 * bereits konvertiert wurden.
	 */
	public void resetBeanConversionCache() {
		getAlreadyConvertedIndicies().clear();
	}

	public int getConvertedBeansCount() {
		return (getAlreadyConvertedIndicies().size());
	}
	
	protected List<?> getRawData() {
		return this.rawData;
	}
	
	protected TableColumnConfig getColumnConfig() {
		return this.columnConfig;
	}
	
	public int getDatasetCount() {
		if (getRawData() == null)
			return -1;
		return getRawData().size();
	}
	
	private void setLeadingBean(Object newLeadingBean) {
		this.leadingBean = newLeadingBean;
	}	
	
	public Object getLeadingBean() {
		return this.leadingBean;
	}	
	
	public void setRawData(List<?> newRawData) {
		this.rawData = newRawData;
	}	
}
