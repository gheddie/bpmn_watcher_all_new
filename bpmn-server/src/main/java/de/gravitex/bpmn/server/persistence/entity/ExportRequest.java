package de.gravitex.bpmn.server.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ExportRequest
{
	@Id
	@GeneratedValue
	private Long id;

	private String requestNumber;

	@Enumerated(EnumType.STRING)
	private ExportRequestStatus requestStatus;

	public ExportRequest()
	{
		super();
	}

	public Long getId()
	{
		return id;
	}

	protected void setId(Long id)
	{
		this.id = id;
	}

	public String getRequestNumber()
	{
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber)
	{
		this.requestNumber = requestNumber;
	}

	public ExportRequestStatus getRequestStatus()
	{
		return requestStatus;
	}

	public void setRequestStatus(ExportRequestStatus requestStatus)
	{
		this.requestStatus = requestStatus;
	}
}
