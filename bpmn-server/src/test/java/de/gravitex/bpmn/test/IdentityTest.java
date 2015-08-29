package de.gravitex.bpmn.test;

import java.util.List;

import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IdentityTest
{
	private static final String PROCESS_DEFINITION_KEY = "IdentityTestProcess";

	// ---

	private static final String GROUP_HUMAN_RESOURCES = "humanResources";

	private static final String GROUP_MANAGEMENT = "management";

	private static final String GROUP_CONTROLLING = "controlling";

	@Rule
	public ProcessEngineRule rule = new ProcessEngineRule();

	@Before
	public void setup()
	{
		System.out.println(" --------- SETTING UP IDENTITY --------- ");

		rule.getIdentityService().newGroup(GROUP_HUMAN_RESOURCES);
		rule.getIdentityService().newGroup(GROUP_CONTROLLING);
		rule.getIdentityService().newGroup(GROUP_MANAGEMENT);

		User user1 = rule.getIdentityService().newUser("user1");
		user1.setFirstName("User");
		user1.setFirstName("Eins");
		rule.getIdentityService().saveUser(user1);
	}

	@Test
	@Deployment(resources = "IdentityTestProcess.bpmn")
	public void testIdentities()
	{
		ProcessInstance processInstance = rule.getRuntimeService()
				.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

		// group tasks
		List<Task> controllingTasks = rule.getTaskService().createTaskQuery()
				.processInstanceId(processInstance.getId())
				.taskCandidateGroup(GROUP_CONTROLLING).list();
		System.out.println("queried " + controllingTasks.size()
				+ " tasks for group controlling...");

		// user tasks
		List<Task> user1Tasks = rule.getTaskService().createTaskQuery()
				.processInstanceId(processInstance.getId())
				.taskCandidateUser("user1").list();
		System.out.println("queried " + controllingTasks.size()
				+ " tasks for user 1...");
	}
}