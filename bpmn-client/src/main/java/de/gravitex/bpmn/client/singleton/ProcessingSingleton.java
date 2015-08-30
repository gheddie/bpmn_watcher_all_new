package de.gravitex.bpmn.client.singleton;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.repository.DiagramLayout;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import de.gravitex.bpmn.server.ProcessEngineProviderRemote;
import de.gravitex.bpmn.server.RMIConstants;
import de.gravitex.bpmn.server.dto.FormFieldDTO;
import de.gravitex.bpmn.server.dto.JobExecutionDTO;
import de.gravitex.bpmn.server.dto.VariableInstanceDTO;
import de.gravitex.bpmn.server.exception.BpmnException;

public class ProcessingSingleton
{

	private static ProcessingSingleton instance;

	private ProcessEngineProviderRemote processEngineProvider;

	private ProcessingSingleton()
	{
		super();
		init();
	}

	private void init()
	{
		Registry registry = null;
		try
		{
			registry = LocateRegistry.getRegistry("localhost",
					RMIConstants.RMI_PORT);
			processEngineProvider = (ProcessEngineProviderRemote) registry
					.lookup(RMIConstants.RMI_ID);
		} catch (RemoteException | NotBoundException e)
		{
			e.printStackTrace();
		}
	}

	public static ProcessingSingleton getInstance()
	{
		if (instance == null)
		{
			instance = new ProcessingSingleton();
		}
		return instance;
	}

	// ---
	
	public List<Task> queryTasks(String businessKey, String taskId) throws RemoteException
	{
		return processEngineProvider.queryTasks(businessKey, taskId);
	}
	
	public List<FormFieldDTO> queryFormFields(String taskId) throws RemoteException
	{
		return processEngineProvider.queryFormFields(taskId);
	}

	public List<Task> queryTasks(String processInstanceId)
			throws RemoteException
	{
		return processEngineProvider.queryTasks(processInstanceId);
	}

	public List<JobExecutionDTO> queryJobs(String processInstanceId)
			throws RemoteException
	{
		return processEngineProvider.queryJobs(processInstanceId);
	}

	public DiagramLayout getDiagramLayout(String processDefinitionId)
			throws RemoteException
	{
		return processEngineProvider.getDiagramLayout(processDefinitionId);
	}

	public List<ProcessDefinition> queryDefinitions() throws RemoteException
	{
		return processEngineProvider.queryDefinitions();
	}

	public void completeTask(String taskId, Map<String, Object> variables)
			throws RemoteException, BpmnException
	{
		processEngineProvider.completeTask(taskId, variables);
	}

	public void correlateMessage(String messageName, String businessKey,
			Map<String, Object> variables) throws RemoteException
	{
		processEngineProvider.correlateMessage(messageName, businessKey,
				variables);
	}
	
	public void triggerSignal(String signalName) throws RemoteException
	{
		processEngineProvider.triggerSignal(signalName);
	}

	public void startProcessInstanceByMessage(String messageName)
			throws RemoteException
	{
		processEngineProvider.startProcessInstanceByMessage(messageName);
	}

	public List<ProcessInstance> queryInstances() throws RemoteException
	{
		return processEngineProvider.queryInstances();
	}

	public void startProcessInstanceByKey(String processDefinitionKey,
			Map<String, Object> variables) throws RemoteException
	{
		processEngineProvider.startProcessInstanceByKey(processDefinitionKey,
				variables);
	}

	public ImageIcon getProcessDiagram(String processDefinitionId)
			throws RemoteException
	{
		return processEngineProvider.getProcessDiagram(processDefinitionId);
	}

	public List<VariableInstanceDTO> queryVariables(String processInstanceId)
			throws RemoteException
	{
		return processEngineProvider.queryVariables(processInstanceId);
	}

	public List<String> queryActivities(String processInstanceId)
			throws RemoteException
	{
		return processEngineProvider.queryActivities(processInstanceId);
	}

	public void deploy(String processKey, boolean addDiagram) throws RemoteException, BpmnException
	{
		processEngineProvider.deploy(processKey, addDiagram);
	}

	public void deployStream(String resourceName, String processKey,
			String version) throws RemoteException
	{
		processEngineProvider.deployStream(resourceName, processKey, version);
	}
}