package de.gravitex.bpmn.client.signaltest;

import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.gravitex.bpmn.server.persistence.HibernateUtil;
import de.gravitex.bpmn.server.persistence.entity.SignalOrder;
import de.gravitex.bpmn.server.persistence.entity.SignalProduct;

public class SignalTestProcessor
{
	private static HashMap<String, String[]> articelNumbers = new HashMap<>();
	static
	{
		articelNumbers.put("123-456", new String[] {"A-111, A-222"});
		articelNumbers.put("234-567", new String[] {"A-333, A-444, A-555, A-666"});
		articelNumbers.put("345-678", new String[] {"A-777, A-888"});
		articelNumbers.put("456-789", new String[] {"A-888"});
	}
	
	public static void main(String[] args)
	{
		for (String key : articelNumbers.keySet())
		{
			processKey(key);
		}
	}

	private static void processKey(String key)
	{
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		SignalOrder order = new SignalOrder();
		order.setOrderNumber(key);
		session.save(order);
//		createOrderProducts(key, order);
		tx.commit();
		session.close();
		
		createOrderProducts(key, order);
	}

	private static void createOrderProducts(String key, SignalOrder order)
	{
		for (String productNumber : articelNumbers.get(key))
		{
			Transaction tx = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			SignalProduct product = new SignalProduct();
			product.setProductNumber(productNumber);
			product.setOrder(order);
			session.save(product);
			tx.commit();
			session.close();
		}
	}
}
