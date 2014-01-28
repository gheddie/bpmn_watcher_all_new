package de.gravitex.bpmn.client.util;

import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import de.gravitex.bpmn.client.util.table.TableColumnConfig;
import de.gravitex.bpmn.client.util.table.TableColumnConfigElement;
import de.gravitex.bpmn.server.dto.VariableInstanceDTO;

public class TableColumnConfigFactory {

	public static TableColumnConfig getDeployedDefinitionsColumn() {
		
		TableColumnConfig config = new TableColumnConfig(ProcessDefinition.class);
		
		config.addPlainTextConfigElement("id", "ID", TableColumnConfigElement.COLUMNTYPE_FULLY_QUALIFIED, false);
		config.addPlainTextConfigElement("name", "Name", TableColumnConfigElement.COLUMNTYPE_FULLY_QUALIFIED, false);
		config.addNumericPlainConfigElement("key", "Key", TableColumnConfigElement.COLUMNTYPE_FULLY_QUALIFIED, false);
		config.addNumericPlainConfigElement("version", "Version", TableColumnConfigElement.COLUMNTYPE_FULLY_QUALIFIED, false);
		
		return config;
	}
	
	public static TableColumnConfig getRunningInstancesColumnConfig() {
		
		TableColumnConfig config = new TableColumnConfig(ProcessInstance.class);
		
		config.addNumericPlainConfigElement("id", "ID", TableColumnConfigElement.COLUMNTYPE_FULLY_QUALIFIED, false);
		config.addPlainTextConfigElement("processDefinitionId", "Process definition id", TableColumnConfigElement.COLUMNTYPE_FULLY_QUALIFIED, false);
		config.addPlainTextConfigElement("businessKey", "Business key", TableColumnConfigElement.COLUMNTYPE_FULLY_QUALIFIED, false);
		
		return config;
	}
	
	public static TableColumnConfig getActualTasksColumnConfig() {
		
		TableColumnConfig config = new TableColumnConfig(Task.class);
		
		config.addPlainTextConfigElement("name", "Name", TableColumnConfigElement.COLUMNTYPE_FULLY_QUALIFIED, false);
		
		return config;
	}

	public static TableColumnConfig getVaribleInstanceColumnConfig() {
		
		TableColumnConfig config = new TableColumnConfig(VariableInstanceDTO.class);
		
		config.addPlainTextConfigElement("name", "Name", TableColumnConfigElement.COLUMNTYPE_FULLY_QUALIFIED, false);
		config.addPlainTextConfigElement("value", "Wert", TableColumnConfigElement.COLUMNTYPE_FULLY_QUALIFIED, false);
		config.addPlainTextConfigElement("variableState", "Status", TableColumnConfigElement.COLUMNTYPE_FULLY_QUALIFIED, false);
		
		return config;
	}
}
