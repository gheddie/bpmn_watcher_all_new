package de.gravitex.bpmn.test;

import java.util.List;

import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactBordersTest
{
	private static final String PROCESS_DEFINITION_KEY = "TransactbordersProcess";
	
	@Rule
	public ProcessEngineRule rule = new ProcessEngineRule();
	
	@Test
	@Deployment(resources = "TransactBorders.bpmn")
	public void testParsingAndDeployment()
	{
		rule.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
		
		//have we got User-Task 'U1'?
		List<Task> taskListAfterStart = rule.getTaskService().createTaskQuery().list();
		assertEquals(1, taskListAfterStart.size());
		
		//have we got User-Task 'U2' and 'U3'?
		Task taskU1 = taskListAfterStart.get(0);
		rule.getTaskService().complete(taskU1.getId());
		assertEquals(2, rule.getTaskService().createTaskQuery().list().size());
	}
}
