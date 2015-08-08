package de.gravitex.bpmn.server.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Refueling
{
	@Id
	@GeneratedValue
	private Long id;
	
	public Long getId()
	{
		return id;
	}
	
	protected void setId(Long id)
	{
		this.id = id;
	}
}
