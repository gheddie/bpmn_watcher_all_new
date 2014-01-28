package de.gravitex.bpmn.server.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class JobExecutionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String activityId;
	
	private Date duedate;

	public JobExecutionDTO(String activityId, Date duedate) {
		this.activityId = activityId;
		this.duedate = duedate;
	}
	
	public String getActivityId() {
		return activityId;
	}
	
	public Date getDuedate() {
		return duedate;
	}

	public String formatRemainingTime() {
		long remaining = duedate.getTime() - Calendar.getInstance().getTimeInMillis();
		return remaining / 1000 + " s";
	}
}
