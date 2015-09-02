package de.gravitex.bpmn.server;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.impl.javax.el.PropertyNotFoundException;
import org.camunda.bpm.engine.management.JobDefinition;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.DiagramElement;
import org.camunda.bpm.engine.repository.DiagramLayout;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.gravitex.bpmn.server.dto.DiagramElementDTO;
import de.gravitex.bpmn.server.dto.FormFieldDTO;
import de.gravitex.bpmn.server.dto.JobExecutionDTO;
import de.gravitex.bpmn.server.dto.VariableInstanceDTO;
import de.gravitex.bpmn.server.dto.VariableState;
import de.gravitex.bpmn.server.exception.BpmnException;
import de.gravitex.bpmn.server.exception.BpmnPropertyException;
import de.gravitex.bpmn.server.singleton.BpmEngine;
import de.gravitex.bpmn.server.util.DelegateHelper;
import de.gravitex.bpmn.server.util.ProviderUtil;

public class ProcessEngineProvider extends UnicastRemoteObject implements
		ProcessEngineProviderRemote
{

	private static final long serialVersionUID = 1L;

	private static final String DEPLOYMENT_UPLOAD_ROOT = "/var/tmp/process_upload/";

	public ProcessEngineProvider() throws RemoteException, BpmnException
	{
		super();
		initProcessEngine();
		deployFromXmlConfigFile();
		try
		{
			bind();
			System.out.println("process server is started...");
		} catch (AlreadyBoundException e)
		{
			e.printStackTrace();
		}
	}

	private void bind() throws AccessException, RemoteException, AlreadyBoundException
	{
		LocateRegistry.createRegistry(RMIConstants.RMI_PORT).bind(RMIConstants.RMI_ID, this);
	}
	
	private void deployFromXmlConfigFile() throws BpmnException
	{
		try
		{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(getClass().getClassLoader()
					.getResourceAsStream("deploy_processes.xml"));
			doc.getDocumentElement().normalize();
			NodeList list = doc.getElementsByTagName("deployment");
			String processName = null;
			boolean enabled = false;
			for (int index=0;index<list.getLength();index++)
			{
				processName = list.item(index).getAttributes().getNamedItem("processname").getNodeValue();
				enabled = Boolean.parseBoolean(list.item(index).getAttributes().getNamedItem("enabled").getNodeValue());
				if (enabled)
				{
					System.out.println("deploying : " + processName);
					deploy(processName, true);
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	private void deployFromConfigFile()
	{
		// TODO : maybe deploy bm xlml config?!?
		try
		{
			InputStream deployResource = getClass().getClassLoader()
					.getResourceAsStream("deploy_processes.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					deployResource));
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				if (line.startsWith("#"))
				{
					System.out.println("ignoring : " + line);
				} else
				{
					System.out.println("deplyoing : " + line);
					deploy(line, true);
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	*/

	public void deploy(String processKey, boolean addDiagram) throws BpmnException
	{
		try
		{
			DeploymentBuilder deployment = BpmEngine.getInstance()
					.getProcessEngine().getRepositoryService().createDeployment()
					.addClasspathResource(processKey + ".bpmn");
			if (addDiagram)
			{
				deployment.addClasspathResource(processKey + ".png");
			}
			deployment.deploy();	
		} catch (ProcessEngineException e)
		{
			throw new BpmnException("error on deployment : " + e.getMessage(), e);
		}
	}

	private void initProcessEngine()
	{
		// H2 via camunda.cfg.xml
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

		// H2
		// ProcessEngine processEngine =
		// ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
		// .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000").setJdbcDriver("org.h2.Driver").setJdbcUsername("sa").setJdbcPassword("").setJobExecutorActivate(true)
		// .setDatabaseSchemaUpdate("true").buildProcessEngine();

		// POSTGRES
//		 ProcessEngine processEngine =
//		 ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
//		 .setJdbcUrl("jdbc:postgresql://localhost/bpmn_watcher").setJdbcDriver("org.postgresql.Driver").setJdbcUsername("postgres").setJdbcPassword("pgvedder").setJobExecutorActivate(true)
//		 .setDatabaseSchemaUpdate("true").buildProcessEngine();

		// MS_SQL
		// processEngine =
		// ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
		// .setJdbcUrl("jdbc:jtds:sqlserver://bcc-sql08-demo:1433/coredb_processing").setJdbcDriver("net.sourceforge.jtds.jdbc.Driver").setJdbcUsername("coredb").setJdbcPassword("coredb").setJobExecutorActivate(true)
		// .setDatabaseSchemaUpdate("true").buildProcessEngine();

		BpmEngine.getInstance().setProcessEngine(processEngine);
	}

	// ---

	public void deployStream(String resourceName, String processKey,
			String version) throws RemoteException
	{
		try
		{
			DeploymentBuilder deployment = BpmEngine.getInstance()
					.getProcessEngine().getRepositoryService()
					.createDeployment();

			String uniqueResourceName = resourceName
					+ System.currentTimeMillis();

			// xml
			FileInputStream xmlStream = new FileInputStream(new File(
					DEPLOYMENT_UPLOAD_ROOT + processKey + "/" + version + "/"
							+ resourceName + ".bpmn"));
			deployment.addInputStream(uniqueResourceName + ".bpmn", xmlStream);

			// png
			FileInputStream pngStream = new FileInputStream(new File(
					DEPLOYMENT_UPLOAD_ROOT + processKey + "/" + version + "/"
							+ resourceName + ".png"));
			deployment.addInputStream(uniqueResourceName + ".png", pngStream);

			deployment.deploy();

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public List<Task> queryTasks(String processInstanceId)
			throws RemoteException
	{
		return BpmEngine.getInstance().getProcessEngine().getTaskService()
				.createTaskQuery().processInstanceId(processInstanceId).list();
	}
	
	@SuppressWarnings("deprecation")
	public List<FormFieldDTO> queryFormFields(String taskId) throws RemoteException
	{
		List<FormFieldDTO> dtos = new ArrayList<>();
		FormFieldDTO dto = null;
		for (FormField formField : BpmEngine.getInstance().getProcessEngine().getFormService().getTaskFormData(taskId).getFormFields())
		{
			dto = new FormFieldDTO();
			dto.setVariableName(formField.getLabel());
			dto.setTypeName(formField.getTypeName());
			dto.setDefaultValue(formField.getDefaultValue());
			dtos.add(dto);
		}
		return dtos;
	}
	
	public List<Task> queryTasks(String businessKey, String taskId) throws RemoteException
	{
		return BpmEngine.getInstance().getProcessEngine().getTaskService()
				.createTaskQuery().processInstanceBusinessKey(businessKey).taskId(taskId).list();
	}

	public List<JobExecutionDTO> queryJobs(String processInstanceId)
			throws RemoteException
	{
		List<JobExecutionDTO> jobDTOs = new ArrayList<>();
		List<Job> queriedJobs = BpmEngine.getInstance().getProcessEngine()
				.getManagementService().createJobQuery()
				.processInstanceId(processInstanceId).list();
		JobDefinition def = null;
		for (Job job : queriedJobs)
		{
			def = BpmEngine.getInstance().getProcessEngine()
					.getManagementService().createJobDefinitionQuery()
					.jobDefinitionId(job.getJobDefinitionId()).singleResult();
			System.out.println("act : " + def.getActivityId());
			jobDTOs.add(new JobExecutionDTO(def.getActivityId(), job
					.getDuedate()));
		}
		return jobDTOs;
	}

	public DiagramLayout getDiagramLayout(String processDefinitionId)
			throws RemoteException
	{
		return BpmEngine.getInstance().getProcessEngine()
				.getRepositoryService()
				.getProcessDiagramLayout(processDefinitionId);
	}

	public List<ProcessDefinition> queryDefinitions() throws RemoteException
	{
		return BpmEngine.getInstance().getProcessEngine()
				.getRepositoryService().createProcessDefinitionQuery().list();
	}

	public void completeTask(String taskId, Map<String, Object> variables)
			throws RemoteException, BpmnException
	{
		try
		{
			BpmEngine.getInstance().getProcessEngine().getTaskService()
					.complete(taskId, variables);
		} catch (ProcessEngineException e)
		{
			if ((e.getCause() != null)
					&& (e.getCause() instanceof PropertyNotFoundException))
			{
				PropertyNotFoundException pnf = (PropertyNotFoundException) e
						.getCause();
				String propertyName = pnf.getMessage().substring(
						pnf.getMessage().indexOf("'") + 1,
						pnf.getMessage().lastIndexOf("'"));
				System.out.println(" @@@ ::: " + propertyName);
				throw new BpmnPropertyException(e, propertyName);
			}
			throw new BpmnException(e);
		}
	}

	public void correlateMessage(String messageName, String businessKey,
			Map<String, Object> variables) throws RemoteException
	{
		BpmEngine.getInstance().getProcessEngine().getRuntimeService()
				.correlateMessage(messageName, businessKey, variables);
	}

	public List<ProcessInstance> queryInstances() throws RemoteException
	{
		return BpmEngine.getInstance().getProcessEngine().getRuntimeService()
				.createProcessInstanceQuery().list();
	}

	public void startProcessInstanceByMessage(String messageName)
			throws RemoteException
	{
		BpmEngine
				.getInstance()
				.getProcessEngine()
				.getRuntimeService()
				.startProcessInstanceByMessage(messageName,
						DelegateHelper.generateBusinessKey());
	}

	public void startProcessInstanceByKey(String processDefinitionKey,
			Map<String, Object> variables) throws RemoteException
	{
		BpmEngine
				.getInstance()
				.getProcessEngine()
				.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey,
						DelegateHelper.generateBusinessKey(), variables);
	}

	public ImageIcon getProcessDiagram(String processDefinitionId)
			throws RemoteException
	{
		InputStream stream = null;
		try
		{
			stream = BpmEngine.getInstance().getProcessEngine()
					.getRepositoryService()
					.getProcessDiagram(processDefinitionId);
			BufferedImage bufferedImage = ImageIO.read(new BufferedInputStream(
					stream));
			if (bufferedImage == null)
			{
				return null;
			}
			return new ImageIcon(bufferedImage);
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public List<VariableInstanceDTO> queryVariables(String processInstanceId)
			throws RemoteException
	{
		List<VariableInstance> variableInstances = BpmEngine.getInstance()
				.getProcessEngine().getRuntimeService()
				.createVariableInstanceQuery()
				.processInstanceIdIn(processInstanceId).list();
		List<VariableInstanceDTO> variableDTOs = new ArrayList<>();
		for (VariableInstance variableInstance : variableInstances)
		{
			variableDTOs.add(new VariableInstanceDTO(
					variableInstance.getName(), variableInstance.getValue(),
					VariableState.DEPLOYED));
		}
		return variableDTOs;
	}

	public List<String> queryActivities(String processInstanceId)
			throws RemoteException
	{
		try
		{
			return BpmEngine.getInstance().getProcessEngine()
					.getRuntimeService()
					.getActiveActivityIds(processInstanceId);
		} catch (ProcessEngineException e)
		{
			System.out.println("error on getting active activity ids : "
					+ e.getMessage());
			return null;
		}
	}

	public void triggerSignal(String signalName) throws RemoteException
	{
		BpmEngine.getInstance().getProcessEngine().getRuntimeService()
				.signalEventReceived(signalName);
	}

	public List<DiagramElementDTO> queryDiagramElements(String processDefinitionId) throws RemoteException
	{
		RepositoryService repositoryService = BpmEngine.getInstance().getProcessEngine().getRepositoryService();
		try
		{
			Document document = ProviderUtil.readXml(repositoryService.getProcessModel(processDefinitionId));
			document.getDocumentElement().normalize();
			NodeList processNodeList = document.getElementsByTagName("bpmn2:process");
			Node processDefinitionNode = null;
			HashMap<String, String> typesByElementId = new HashMap<>();
			for (int processIndex = 0; processIndex < processNodeList.getLength(); processIndex++)
			{
				processDefinitionNode = processNodeList.item(processIndex);
				//search 'bpmn2:process' node for elemts...
				NodeList elementNodes = processDefinitionNode.getChildNodes();
				Node elementNode = null;
				Element element = null;
				for (int elementIndex = 0; elementIndex < elementNodes.getLength(); elementIndex++)
				{
					elementNode = elementNodes.item(elementIndex);
					if (elementNode instanceof Element)
					{
						element = (Element) elementNode;
						if (element.getAttribute("id") != null)
						{
							System.out.println(element.getAttribute("id") + " :: " + elementNode.getNodeName());
							typesByElementId.put(element.getAttribute("id"), elementNode.getNodeName());
						}
					}
				}
			}
			Map<String, DiagramElement> elements = repositoryService.getProcessDiagramLayout(processDefinitionId).getElements();
			List<DiagramElementDTO> dtos = new ArrayList<>();
			for (DiagramElement element : elements.values())
			{
				System.out.println("element '"+element.getId()+"' is a '"+typesByElementId.get(element.getId())+"'.");
				dtos.add(new DiagramElementDTO(element, typesByElementId.get(element.getId())));
			}
			return dtos;
		} catch (SAXException | IOException | ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}