package de.gravitex.bpmn.test.troubleticket.old;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import de.gravitex.bpmn.server.commonvalues.ProcessVariables;
import de.gravitex.bpmn.test.CustomizedProcessEngineTest;

public class StartTroubleTest extends CustomizedProcessEngineTest
{
	private static final String PROCESS_DEFINITION_KEY_MAIN = "StartTroubleProcess";
	
	private static final String PROCESS_DEFINITION_KEY_SUB = "SolveAttempt";
	
	private static final String MSG_CHANGE_RESPONSIBILITY = "MSG_CHANGE_RESPONSIBILITY";
	
	private static final String ESCALATION_TIMER_ID = "EscalationTimerBoundaryEvent";
	
	@Rule
	public ProcessEngineRule rule = new ProcessEngineRule();

	@Test
	@Deployment(resources = {"troubleticket/StartTrouble.bpmn", "troubleticket/SolveAttempt.bpmn"})
	public void testParsingAndDeployment()
	{
		//...
	}
	
	@Test
	@Deployment(resources = {"troubleticket/StartTrouble.bpmn", "troubleticket/SolveAttempt.bpmn"})
	public void testEscalationLevels()
	{
		ProcessInstance processInstanceMain = rule.getRuntimeService().startProcessInstanceByKey(
				PROCESS_DEFINITION_KEY_MAIN);
		
		//there should be one job...
		List<Job> jobList = rule.getManagementService().createJobQuery().processInstanceId(processInstanceMain.getId()).list();
		assertEquals(1, jobList.size());
		
		//finish job --> escalation level should be '1'
		rule.getManagementService().executeJob(jobList.get(0).getId());
		assertEquals(new Integer(1), getVariableValueByProcessInstance(processInstanceMain, ProcessVariables.TroubleTicketNew.VAR_ESCALATION_LEVEL, rule));
		
		//there should be a new job to count for escalation level '2'
//		assertEquals(1, rule.getManagementService().createJobQuery().processInstanceId(processInstance.getId()).list().size());
	}
	
	@Test(expected=MismatchingMessageCorrelationException.class)
	@Deployment(resources = {"troubleticket/StartTrouble.bpmn", "troubleticket/SolveAttempt.bpmn"})
	public void testCallActitivity()
	{
		ProcessInstance processInstance = rule.getRuntimeService().startProcessInstanceByKey(
				PROCESS_DEFINITION_KEY_MAIN);
		
		//starting main process should start sub process, too...
		List<ProcessInstance> subProcessList = rule.getRuntimeService().createProcessInstanceQuery().processDefinitionKey(PROCESS_DEFINITION_KEY_SUB).list();
		assertEquals(1, subProcessList.size());
		
		//do we have 'Classify ticket' task in sub process?
		ensureUserTaskGenerated("Classify ticket", subProcessList.get(0).getId(), rule);
		
		//check business key of sub process (inherited from parent process)
		assertEquals(processInstance.getBusinessKey(), subProcessList.get(0).getBusinessKey());
		
		//this must throw a 'MismatchingMessageCorrelationException'...
		rule.getRuntimeService().correlateMessage(MSG_CHANGE_RESPONSIBILITY, processInstance.getBusinessKey());
	}
	
	@Test
	@Deployment(resources = {"troubleticket/StartTrouble.bpmn", "troubleticket/SolveAttempt.bpmn"})
	public void testCorrelateChangeResponsibilityMessage() throws Exception
	{
		//start main process
		ProcessInstance processInstanceMain = rule.getRuntimeService().startProcessInstanceByKey(
				PROCESS_DEFINITION_KEY_MAIN);
		
		ProcessInstance processInstanceSub = rule.getRuntimeService().createProcessInstanceQuery().processDefinitionKey(PROCESS_DEFINITION_KEY_SUB).singleResult();
		
		finishNextTask(processInstanceSub, "Classify ticket", rule, createVariablesSimple(ProcessVariables.TroubleTicket.VAR_TICKET_ASSIGNEE_GROUP, "123"));
		
		finishNextTask(processInstanceSub, "Check classification", rule, createVariablesSimple(ProcessVariables.TroubleTicket.VAR_TICKET_ACCEPTED, true));
		
		//correlate change responsibility message must generate 'Classify ticket' task...
		rule.getRuntimeService().correlateMessage(MSG_CHANGE_RESPONSIBILITY, processInstanceMain.getBusinessKey());	
		ensureUserTaskGenerated("Classify ticket", processInstanceSub.getId(), rule);
	}
}