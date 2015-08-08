package de.gravitex.bpmn.server.test;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import de.gravitex.bpmn.server.processhelper.exportrequest.ExportRequestStatus;

public class HibernateTest
{
	public static void main(String[] args)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		ExportRequest req = new ExportRequest();
		req.setRequestNumber("AAA-BBB-GGG");
		req.setRequestStatus(ExportRequestStatus.UNPROCESSED);
		session.save(req);

		session.getTransaction().commit();

		Query q = session.createQuery("From ExportRequest");

		List<ExportRequest> resultList = q.list();
		System.out.println("num of employess:" + resultList.size());
		for (ExportRequest request : resultList)
		{
			System.out.println("next export request : " + request);
		}
	}
}
