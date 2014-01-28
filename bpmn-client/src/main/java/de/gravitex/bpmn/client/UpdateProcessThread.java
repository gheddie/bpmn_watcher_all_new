package de.gravitex.bpmn.client;

public class UpdateProcessThread extends Thread {
	
	//1 Sekunde
	protected static final long POLL_FREQUENCY_CLIENT = 1000;
	
	private EnhandedProcessFrame callBack;

	public UpdateProcessThread(EnhandedProcessFrame callBack) {
		super();
		this.callBack = callBack;
	}

	public void run() {
		super.run();
		while(true) {
			try {
				Thread.sleep(POLL_FREQUENCY_CLIENT);
				callBack.refreshProcessPanel();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
}
