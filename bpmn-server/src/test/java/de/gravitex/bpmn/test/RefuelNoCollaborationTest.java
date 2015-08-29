package de.gravitex.bpmn.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;
import de.gravitex.bpmn.server.per.HibernateUtil;
import de.gravitex.bpmn.server.persistence.entity.Refueling;
import de.gravitex.bpmn.server.persistence.entity.RefuelingStatus;

public class RefuelNoCollaborationTest extends CustomizedProcessEngineTest
{
	private static final String PROCESS_DEFINITION_KEY = "RefuelNoCollab";

	private static final String PIN_INVALID = "1234";

	private static final String PIN_VALID = "0815";

	@Rule
	public ProcessEngineRule rule = new ProcessEngineRule();
	
	@Before
	public void before()
	{
		clearRefuelings();
	}

	@Test
	@Deployment(resources = "RefuelNoCollaboration.bpmn")
	public void testParsingAndDeployment()
	{
		rule.getRuntimeService().startProcessInstanceByKey(
				PROCESS_DEFINITION_KEY);
	}

	@Test
	@Deployment(resources = "RefuelNoCollaboration.bpmn")
	public void testExceedPinAttempts() throws Exception
	{
		ProcessInstance processInstance = rule.getRuntimeService()
				.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

		finishEnterPinTask(PIN_INVALID, processInstance.getId());
		// pin counter should be 1
		assertEquals(
				1,
				getVariableValueByProcessInstance(processInstance,
						ProcessVariables.RefNoCollabVars.VAR_PIN_ATTEMPTS, rule));

		finishEnterPinTask(PIN_INVALID, processInstance.getId());
		// pin counter should be 2
		assertEquals(
				2,
				getVariableValueByProcessInstance(processInstance,
						ProcessVariables.RefNoCollabVars.VAR_PIN_ATTEMPTS, rule));

		finishEnterPinTask(PIN_INVALID, processInstance.getId());
		
		ensureProcessInstanceFinished(processInstance, rule);
	}

	@Test(expected=Exception.class)
	@Deployment(resources = "RefuelNoCollaboration.bpmn")
	public void testPumpExclusive() throws Exception
	{
		ProcessInstance p1 = rule.getRuntimeService()
				.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
		ProcessInstance p2 = rule.getRuntimeService()
				.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

		finishEnterPinTask(PIN_VALID, p1.getId());
		finishEnterPinTask(PIN_VALID, p2.getId());

		finishChoosePumpTask(new Integer(2), p1.getId());
		finishChoosePumpTask(new Integer(2), p2.getId());
	}
	
	@Test
	@Deployment(resources = "RefuelNoCollaboration.bpmn")
	public void testRefuelingStates() throws Exception
	{
		ProcessInstance processInstance = rule.getRuntimeService()
				.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
		
		assertEquals(RefuelingStatus.INITIALIZED, getRefuelingStatus(processInstance));
		
		finishEnterPinTask(PIN_VALID, processInstance.getId());

		finishChoosePumpTask(new Integer(2), processInstance.getId());
		
		assertEquals(RefuelingStatus.REFUELING, getRefuelingStatus(processInstance));
	}
	
	@Test
	@Deployment(resources = "RefuelNoCollaboration.bpmn")
	public void testExceedTimer() throws Exception
	{
		ProcessInstance processInstance = rule.getRuntimeService()
				.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
		
		finishEnterPinTask(PIN_VALID, processInstance.getId());
		
		finishChoosePumpTask(new Integer(3), processInstance.getId());
		
		finishNextTask(processInstance, "Remove card", rule);
		
		//finish timer
		List<Job> jobList = rule.getManagementService().createJobQuery().processInstanceId(processInstance.getId()).list();
		
		//there should be one job...
		assertEquals(1, jobList.size());
		
		//fire job
		rule.getManagementService().executeJob(jobList.get(0).getId());
		
		//ensure process is gone
		ensureProcessInstanceFinished(processInstance, rule);
	}

	private RefuelingStatus getRefuelingStatus(ProcessInstance processInstance)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		String queryString = "From "+Refueling.class.getSimpleName()+" ref where ref.id = " + getVariableValueByProcessInstance(processInstance, ProcessVariables.RefNoCollabVars.VAR_REFUELING_PROCESS_ID, rule);
		Query q = session.createQuery(queryString);
		Refueling refueling = (Refueling) q.list().get(0);
		session.close();
		return refueling.getRefuelingStatus();
	}

	private void finishChoosePumpTask(Integer pumpNo, String processInstanceId) throws Exception
	{
		Task choosePumpTask = rule.getTaskService().createTaskQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (!(choosePumpTask.getName().equals("Choose fuel pump")))
		{
			throw new Exception("wrong name ('" + choosePumpTask.getName()
					+ "') for enter choose pump task!!");
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ProcessVariables.RefNoCollabVars.VAR_CHOSEN_PUMP_NO,
				pumpNo);
		rule.getTaskService().complete(choosePumpTask.getId(), variables);
	}

	private void finishEnterPinTask(String pin, String processInstanceId) throws Exception
	{
		Task enterPinTask = rule.getTaskService().createTaskQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (!(enterPinTask.getName().equals("Enter PIN")))
		{
			throw new Exception("wrong name ('" + enterPinTask.getName()
					+ "') for enter pin task!!");
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ProcessVariables.RefNoCollabVars.VAR_PROVIDED_PIN, pin);
		rule.getTaskService().complete(enterPinTask.getId(), variables);
	}

	@After
	public void after()
	{
		clearRefuelings();
	}
	
	private void clearRefuelings()
	{
		System.out.println("clearing refuelings...");
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		session.createSQLQuery(
				"delete from " + Refueling.class.getSimpleName() + ";")
				.executeUpdate();
		tx.commit();
		session.close();
	}
}
