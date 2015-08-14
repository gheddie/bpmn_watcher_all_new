package de.gravitex.bpmn.server.processhelper.asynchronousinvoice;

import org.hibernate.Query;
import org.hibernate.Session;

import de.gravitex.bpmn.server.per.HibernateUtil;
import de.gravitex.bpmn.server.persistence.entity.MySystemProperty;

public class AsyncInvoiceHelper
{
	private static final String KEY_OFFICE_OPEN = "officeOpened";

	public static boolean isPostOfficeOpen()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("From "+MySystemProperty.class.getSimpleName()+" prop where prop.key = '"+KEY_OFFICE_OPEN+"'");
		MySystemProperty officeOpenProperty = (MySystemProperty) q.list().get(0);
		session.close();
		return Boolean.parseBoolean(officeOpenProperty.getValue());
	}
	
	//---
	
	public static void main(String[] args)
	{
		System.out.println("open :" + AsyncInvoiceHelper.isPostOfficeOpen());
	}
}
