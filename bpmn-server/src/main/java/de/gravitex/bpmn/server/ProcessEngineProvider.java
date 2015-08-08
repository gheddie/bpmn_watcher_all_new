package de.gravitex.bpmn.server;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.javax.el.PropertyNotFoundException;
import org.camunda.bpm.engine.management.JobDefinition;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.DiagramLayout;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;

import de.gravitex.bpmn.server.dto.JobExecutionDTO;
import de.gravitex.bpmn.server.dto.VariableInstanceDTO;
import de.gravitex.bpmn.server.dto.VariableState;
import de.gravitex.bpmn.server.exception.BpmnException;
import de.gravitex.bpmn.server.exception.BpmnPropertyException;
import de.gravitex.bpmn.server.singleton.BpmEngine;
import de.gravitex.bpmn.server.util.DelegateHelper;

public class ProcessEngineProvider extends UnicastRemoteObject implements ProcessEngineProviderRemote {
	
	private static final long serialVersionUID = 1L;

	private static final String DEPLOYMENT_UPLOAD_ROOT = "/var/tmp/process_upload/";
	
//	private ProcessEngine processEngine;
	
	public ProcessEngineProvider() throws RemoteException {
		super();
		init();
	}

	private void init() {
		initProcessEngine();
		//---
//		deploy("SimpleProcess", true);
//		deploy("TestCollaboration", true);
//		deploy("CollaborationSend", true);
//		deploy("CollaborationReturn", true);
//		deploy("diagram", true);
//		deploy("Revoke", true);
//		deploy("SimpleDecision", true);
		deploy("RefuelNoCollaboration", true);
//		deploy("CashMachine", true);
//		deploy("ComplexGatewayTest", true);
//		deploy("SignalTest", true);
//		deploy("SynchronisationTest", true);
//		deploy("ExportRequestTriggerProcess", true);
//		deploy("ExportRequestEvaluationProcess", true);
//		deploy("CompensationTest", true);
	}

	private void deploy(String processKey, boolean addDiagram) {
		DeploymentBuilder deployment = BpmEngine.getInstance().getProcessEngine().getRepositoryService().createDeployment().addClasspathResource(processKey+".bpmn");
		if (addDiagram) {
			deployment.addClasspathResource(processKey+".png");
		}
		deployment.deploy();
	}

	private void initProcessEngine() {
		
		//H2
		ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
				.setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000").setJdbcDriver("org.h2.Driver").setJdbcUsername("sa").setJdbcPassword("").setJobExecutorActivate(true)
				.setDatabaseSchemaUpdate("true").buildProcessEngine();
		
		//POSTGRES
//		ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
//				.setJdbcUrl("jdbc:postgresql://localhost/bpmn_watcher").setJdbcDriver("org.postgresql.Driver").setJdbcUsername("postgres").setJdbcPassword("pgvedder").setJobExecutorActivate(true)
//				.setDatabaseSchemaUpdate("true").buildProcessEngine();	
		
		//MS_SQL
//		processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
//				.setJdbcUrl("jdbc:jtds:sqlserver://bcc-sql08-demo:1433/coredb_processing").setJdbcDriver("net.sourceforge.jtds.jdbc.Driver").setJdbcUsername("coredb").setJdbcPassword("coredb").setJobExecutorActivate(true)
//				.setDatabaseSchemaUpdate("true").buildProcessEngine();	
		
		BpmEngine.getInstance().setProcessEngine(processEngine);
	}
	
	//---
	
	public void deployStream(String resourceName, String processKey, String version) throws RemoteException {
		try {
			DeploymentBuilder deployment = BpmEngine.getInstance().getProcessEngine().getRepositoryService().createDeployment();
			
			String uniqueResourceName = resourceName + System.currentTimeMillis();
			
			//xml
			FileInputStream xmlStream = new FileInputStream(new File(DEPLOYMENT_UPLOAD_ROOT + processKey + "/" + version + "/" + resourceName+".bpmn"));
			deployment.addInputStream(uniqueResourceName+".bpmn", xmlStream);
			
			//png
			FileInputStream pngStream = new FileInputStream(new File(DEPLOYMENT_UPLOAD_ROOT + processKey + "/" + version + "/" + resourceName+".png"));
			deployment.addInputStream(uniqueResourceName+".png", pngStream);
			
			deployment.deploy();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<Task> queryTasks(String processInstanceId) throws RemoteException {
		return BpmEngine.getInstance().getProcessEngine().getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
	}
	
	public List<JobExecutionDTO> queryJobs(String processInstanceId) throws RemoteException {
		List<JobExecutionDTO> jobDTOs = new ArrayList<>();
		List<Job> queriedJobs = BpmEngine.getInstance().getProcessEngine().getManagementService().createJobQuery().processInstanceId(processInstanceId).list();
		JobDefinition def = null;
		for (Job job : queriedJobs) {
			def = BpmEngine.getInstance().getProcessEngine().getManagementService().createJobDefinitionQuery().jobDefinitionId(job.getJobDefinitionId()).singleResult();
			System.out.println("act : " + def.getActivityId());
			jobDTOs.add(new JobExecutionDTO(def.getActivityId(), job.getDuedate()));
		}
		return jobDTOs;
	}

	public DiagramLayout getDiagramLayout(String processDefinitionId) throws RemoteException {
		return BpmEngine.getInstance().getProcessEngine().getRepositoryService().getProcessDiagramLayout(processDefinitionId);
	}

	public List<ProcessDefinition> queryDefinitions() throws RemoteException {
		return BpmEngine.getInstance().getProcessEngine().getRepositoryService().createProcessDefinitionQuery().list();
	}

	public void completeTask(String taskId, Map<String, Object> variables) throws RemoteException, BpmnException {
		try {
			BpmEngine.getInstance().getProcessEngine().getTaskService().complete(taskId, variables);
		} catch (ProcessEngineException e) {
			if ((e.getCause() != null) && (e.getCause() instanceof PropertyNotFoundException))
			{
				PropertyNotFoundException pnf = (PropertyNotFoundException) e.getCause();
				String propertyName = pnf.getMessage().substring(pnf.getMessage().indexOf("'")+1, pnf.getMessage().lastIndexOf("'"));
				System.out.println(" @@@ ::: " + propertyName);
				throw new BpmnPropertyException(e, propertyName);
			}
			throw new BpmnException(e);
		}
	}

	public void correlateMessage(String messageName, String businessKey, Map<String, Object> variables) throws RemoteException {
		BpmEngine.getInstance().getProcessEngine().getRuntimeService().correlateMessage(messageName, businessKey, variables);
	}

	public List<ProcessInstance> queryInstances() throws RemoteException {
		return BpmEngine.getInstance().getProcessEngine().getRuntimeService().createProcessInstanceQuery().list();
	}
	
	public void startProcessInstanceByMessage(String messageName) throws RemoteException {
		BpmEngine.getInstance().getProcessEngine().getRuntimeService().startProcessInstanceByMessage(messageName, DelegateHelper.generateBusinessKey());
	}

	public void startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables) throws RemoteException {
		BpmEngine.getInstance().getProcessEngine().getRuntimeService().startProcessInstanceByKey(processDefinitionKey, DelegateHelper.generateBusinessKey(), variables);
	}

	public ImageIcon getProcessDiagram(String processDefinitionId) throws RemoteException {
		InputStream stream = null;
		try {
			stream = BpmEngine.getInstance().getProcessEngine().getRepositoryService().getProcessDiagram(processDefinitionId);
			BufferedImage bufferedImage = ImageIO.read(new BufferedInputStream(stream));
			if (bufferedImage == null) {
				return null;
			}
			return new ImageIcon(bufferedImage);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<VariableInstanceDTO> queryVariables(String processInstanceId) throws RemoteException {
		List<VariableInstance> variableInstances = BpmEngine.getInstance().getProcessEngine().getRuntimeService().createVariableInstanceQuery().processInstanceIdIn(processInstanceId).list();
		List<VariableInstanceDTO> variableDTOs = new ArrayList<>();
		for (VariableInstance variableInstance : variableInstances) {
			variableDTOs.add(new VariableInstanceDTO(variableInstance.getName(), variableInstance.getValue(), VariableState.DEPLOYED));
		}
		return variableDTOs;
	}

	public List<String> queryActivities(String processInstanceId) throws RemoteException {
		try {
			return BpmEngine.getInstance().getProcessEngine().getRuntimeService().getActiveActivityIds(processInstanceId);	
		} catch (ProcessEngineException e) {
			System.out.println("error on getting active activity ids : " + e.getMessage());
			return null;
		}		
	}
}
