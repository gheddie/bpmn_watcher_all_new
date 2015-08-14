package de.gravitex.bpmn.server.processhelper.asynchronousinvoice;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
		if (officeOpenProperty.getValue().equals("1"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private static void openOffice()
	{
		openOrCloseOffice("1");
	}

	private static void closeOffice()
	{
		openOrCloseOffice("0");
	}
	
	private static void openOrCloseOffice(String value)
	{
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		session.createSQLQuery(
				"update mysystemproperty set value = '" + value
						+ "' where key = '"+KEY_OFFICE_OPEN+"';")
				.executeUpdate();
		tx.commit();
		session.close();
	}

	//---
	
	public static void main(String[] args)
	{
//		closeOffice();
		
		openOffice();
		
		System.out.println(" @@@ office open : " + AsyncInvoiceHelper.isPostOfficeOpen());
	}
}
