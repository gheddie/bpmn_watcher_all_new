package de.gravitex.bpmn.server.processhelper.refueling;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.gravitex.bpmn.server.persistence.HibernateUtil;
import de.gravitex.bpmn.server.persistence.entity.Refueling;
import de.gravitex.bpmn.server.persistence.entity.RefuelingStatus;

public class RefuelingHelper
{
	public static Refueling createRefueling()
	{
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		Refueling refueling = new Refueling();
		refueling.setRefuelingStatus(RefuelingStatus.INITIALIZED);
		session.save(refueling);
		tx.commit();
		session.close();
		return refueling;
	}

	public static void reservePump(Long refuelingProcessId, int pumpNo)
			throws Exception
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("From " + Refueling.class.getSimpleName()
				+ " rf WHERE rf.pumpNo = " + pumpNo
				+ " AND rf.refuelingStatus = '" + RefuelingStatus.REFUELING
				+ "'");
		List<Refueling> list = q.list();
		if ((list != null) && (list.size() > 0))
		{
			throw new Exception("pump no " + pumpNo + " is already occupied!!");
		}
		// set pump no
		Transaction tx = session.beginTransaction();
		session.createSQLQuery(
				"update refueling set pumpno = " + pumpNo
						+ ", refuelingStatus = '" + RefuelingStatus.REFUELING
						+ "' where id = " + refuelingProcessId).executeUpdate();
		tx.commit();
		session.close();
	}

	public static void finishProcess(Long refuelingProcessId,
			RefuelingStatus refuelingStatus, int errorCode)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String queryString = null;
		if (errorCode >= 0)
		{
			queryString = "update refueling set refuelingstatus = '"
					+ refuelingStatus + "', errorcode = '" + errorCode
					+ "' where id = '" + refuelingProcessId + "';";
		} else
		{
			queryString = "update refueling set refuelingstatus = '"
					+ refuelingStatus + "' where id = '" + refuelingProcessId
					+ "';";
		}
		session.createSQLQuery(queryString).executeUpdate();
		tx.commit();
		session.close();
	}
}
