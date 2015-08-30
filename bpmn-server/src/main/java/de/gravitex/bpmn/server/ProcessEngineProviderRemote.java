package de.gravitex.bpmn.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.camunda.bpm.engine.repository.DiagramElement;
import org.camunda.bpm.engine.repository.DiagramLayout;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import de.gravitex.bpmn.server.dto.FormFieldDTO;
import de.gravitex.bpmn.server.dto.JobExecutionDTO;
import de.gravitex.bpmn.server.dto.VariableInstanceDTO;
import de.gravitex.bpmn.server.exception.BpmnException;

public interface ProcessEngineProviderRemote extends Remote {

	public List<Task> queryTasks(String processInstanceId) throws RemoteException;
	
	public List<FormFieldDTO> queryFormFields(String taskId) throws RemoteException;
	
	public List<Task> queryTasks(String businessKey, String taskName) throws RemoteException;
	
	public List<JobExecutionDTO> queryJobs(String processInstanceId) throws RemoteException;

	public DiagramLayout getDiagramLayout(String processDefinitionId) throws RemoteException;

	public List<ProcessDefinition> queryDefinitions() throws RemoteException;

	public void completeTask(String taskId, Map<String, Object> variables) throws RemoteException, BpmnException;

	public void correlateMessage(String messageName, String businessKey, Map<String, Object> variables) throws RemoteException;

	public void startProcessInstanceByMessage(String messageName) throws RemoteException;

	public List<ProcessInstance> queryInstances() throws RemoteException;

	public void startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables) throws RemoteException;

	public ImageIcon getProcessDiagram(String processDefinitionId) throws RemoteException;

	public List<VariableInstanceDTO> queryVariables(String processInstanceId) throws RemoteException;

	public List<String> queryActivities(String processInstanceId) throws RemoteException;
	
	public void deploy(String processKey, boolean addDiagram) throws RemoteException, BpmnException;
	
	public void deployStream(String resourceName, String processKey, String version) throws RemoteException;
	
	public void triggerSignal(String signalName) throws RemoteException;

	public Map<String, DiagramElement> queryDiagramElements(String processDefinition) throws RemoteException;
}