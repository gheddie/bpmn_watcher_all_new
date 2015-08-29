package de.gravitex.bpmn.test;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

public class CompensationTestTest
{
	@Rule
	public ProcessEngineRule rule = new ProcessEngineRule();
	
	@Test
	@Deployment(resources = "CompensationTest.bpmn")
	public void testParsingAndDeployment()
	{

	}
}