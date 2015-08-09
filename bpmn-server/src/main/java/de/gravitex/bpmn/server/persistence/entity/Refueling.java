package de.gravitex.bpmn.server.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Refueling
{
	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private RefuelingStatus refuelingStatus;
	
	private int pumpNo;
	
	private int errorCode;
	
	public Long getId()
	{
		return id;
	}
	
	protected void setId(Long id)
	{
		this.id = id;
	}
	
	public RefuelingStatus getRefuelingStatus()
	{
		return refuelingStatus;
	}
	
	public void setRefuelingStatus(RefuelingStatus refuelingStatus)
	{
		this.refuelingStatus = refuelingStatus;
	}
	
	public int getPumpNo()
	{
		return pumpNo;
	}
	
	public void setPumpNo(int pumpNo)
	{
		this.pumpNo = pumpNo;
	}
	
	public int getErrorMessage()
	{
		return errorCode;
	}
	
	public void setErrorMessage(int errorCode)
	{
		this.errorCode = errorCode;
	}
}
