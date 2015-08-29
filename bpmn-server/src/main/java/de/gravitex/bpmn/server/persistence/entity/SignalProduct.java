package de.gravitex.bpmn.server.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SignalProduct
{
	private String productNumber;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private SignalOrder order;
	
	public Long getId()
	{
		return id;
	}
	
	protected void setId(Long id)
	{
		this.id = id;
	}

	public String getProductNumber()
	{
		return productNumber;
	}
	
	public void setProductNumber(String productNumber)
	{
		this.productNumber = productNumber;
	}
	
	public SignalOrder getOrder()
	{
		return order;
	}
	
	public void setOrder(SignalOrder order)
	{
		this.order = order;
	}
}
