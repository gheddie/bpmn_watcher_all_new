package de.gravitex.bpmn.server.processhelper.refueling;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.gravitex.bpmn.server.per.HibernateUtil;
import de.gravitex.bpmn.server.persistence.entity.Refueling;

public class RefuelingHelper
{
	public static Refueling createRefueling()
	{
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		Refueling refueling = new Refueling();
		session.save(refueling);
		tx.commit();
		session.close();
		return refueling;
	}

	public static boolean isPumpReserved(int pumpNo)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("From " + Refueling.class.getSimpleName()
				+ " rf");
		List<Refueling> list = q.list();
		session.close();
		return ((list != null) && (list.size() > 0));
	}
	
	public static void reservePump(Long refuelingProcessId, int pumpNo)
	{
		
	}
	
	public static void finishRefuelingProcess(Long refuelingProcessId)
	{
		
	}
}
