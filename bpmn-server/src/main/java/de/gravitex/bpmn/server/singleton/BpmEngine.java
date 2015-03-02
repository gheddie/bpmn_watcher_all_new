package de.gravitex.bpmn.server.singleton;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;

public class BpmEngine {

	private static BpmEngine instance;
	
	private ProcessEngine processEngine;

	public static synchronized BpmEngine getInstance() {
		if (instance == null) {
			instance = new BpmEngine();
		}
		return instance;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public ProcessEngine getProcessEngine() {
		return this.processEngine;
	}

	public RuntimeService runtime() {
		return processEngine.getRuntimeService();
	}
}
