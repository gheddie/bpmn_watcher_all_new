package de.gravitex.bpmn.client.util.table;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.apache.log4j.Logger;

public class GenericBpmnTable extends JTable {
	
	private static Logger			logger					= Logger.getLogger(GenericBpmnTable.class);

	private static final long serialVersionUID = 1L;
	
	private static final String	POSTFIX_TABLE_SELECTED_BEAN			= "BeanSelected";

	private JFrame relatedView;

	private String relatedMethod;

	private List<?> data;
	
	public GenericBpmnTable() {
		super();
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public void setData(List<?> data, TableColumnConfig config) {
		this.data = data;
		try {
			setModel(new TableDataBeanConverter(data, config).buildModel());
		} catch (MvcGuiComponentInitializationIncompleteException e) {
			e.printStackTrace();
		}
	}
	
	public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
		super.changeSelection(rowIndex, columnIndex, toggle, extend);
		logger.trace("selected row :: " + rowIndex);
		String tmpMethodName = relatedMethod + POSTFIX_TABLE_SELECTED_BEAN;
		try {
			ReflectionHelperMethods.invokeMethodIfFound(relatedView, tmpMethodName, new Class<?>[] {Object.class}, new Object[] {data.get(rowIndex)});
		} catch (NoSuchMethodException e) {
			logger.error(e);
		}
	}

	public void setRelatedMethod(String relatedMethod) {
		this.relatedMethod = relatedMethod;
	}

	public void setRelatedView(JFrame relatedView) {
		this.relatedView = relatedView;
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
