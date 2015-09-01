package de.gravitex.bpmn.server.processhelper.exportrequest;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.gravitex.bpmn.server.persistence.HibernateUtil;
import de.gravitex.bpmn.server.persistence.entity.ExportRequest;
import de.gravitex.bpmn.server.persistence.entity.ExportRequestStatus;

public class ExportRequestHelper
{
	@SuppressWarnings("unchecked")
	public static List<ExportRequest> queryUnprocessedExportRequests()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("From "+ExportRequest.class.getSimpleName()+" exr where exr.requestStatus = '"+ExportRequestStatus.UNPROCESSED+"'");
		List<ExportRequest> list = q.list();
		session.close();
		return list;
	}

	public static void updateRequestStatus(String requestNumber,
			ExportRequestStatus status)
	{
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		session.createSQLQuery(
				"update exportrequest set requeststatus = '" + status
						+ "' where requestnumber = '" + requestNumber + "';")
				.executeUpdate();
		tx.commit();
		session.close();
	}
}
