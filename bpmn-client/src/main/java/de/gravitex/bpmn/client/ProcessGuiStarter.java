package de.gravitex.bpmn.client;

import org.apache.log4j.PropertyConfigurator;

public class ProcessGuiStarter {
	
//	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("C:\\log4j_props\\generic_log4j.properties");
		new EnhandedProcessFrame();
	}
}
