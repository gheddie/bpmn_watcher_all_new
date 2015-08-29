package de.gravitex.bpmn.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.ProcessEngineRule;

public class CustomizedProcessEngineTest
{
	protected Object getVariableValueByProcessInstance(ProcessInstance processInstance, String variableName, ProcessEngineRule rule)
	{
		VariableInstance result = rule.getRuntimeService().createVariableInstanceQuery().processInstanceIdIn(processInstance.getId()).variableName(variableName).singleResult();
		if (result == null)
		{
			return null;
		}
		return result.getValue();
	}
	
	protected void finishNextTask(ProcessInstance processInstance, String taskName, ProcessEngineRule rule) throws Exception
	{
		finishNextTask(processInstance, taskName, rule, null);
	}
	
	protected void finishNextTask(ProcessInstance processInstance, String taskName, ProcessEngineRule rule, Map<String, Object> variables) throws Exception
	{
		Task task = rule.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		if (!(taskName.equals(task.getName())))
		{
			throw new Exception("excepted task name ('"+taskName+"') does not match actual ('"+task.getName()+"')!");
		}
		//finish it...
		rule.getTaskService().complete(task.getId(), variables);
	}
	
	protected void ensureProcessInstanceFinished(ProcessInstance processInstance, ProcessEngineRule rule)
	{
		assertEquals(null,
				rule.getRuntimeService().createProcessInstanceQuery()
						.processInstanceId(processInstance.getId())
						.singleResult());
	}
	
	protected void ensureUserTaskGenerated(String taskName, String processInstanceId,
			ProcessEngineRule rule)
	{
		assertEquals(1, rule.getTaskService().createTaskQuery().processInstanceId(processInstanceId).taskName(taskName).list().size());
	}
	
	protected Map<String, Object> createVariablesSimple(String variableName, Object value)
	{
		HashMap<String, Object> variables = new HashMap<String, Object>();
		variables.put(variableName, value);
		return variables;
	}
}
