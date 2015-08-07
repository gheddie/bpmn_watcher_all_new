package de.gravitex.bpmn.server.processhelper.exportrequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExportRequestDbConnector
{
	public static final String DB_URL = "jdbc:postgresql://localhost/postgres";

	public static final String DB_USER = "postgres";

	public static final String DB_PASSWD = "pgvedder";

	public static final String DB_DRIVER = "org.postgresql.Driver";
	
	private static final int COL_INDEX_ID = 1;
	
	private static final int COL_INDEX_REQUEST_NUMBER = 2;

	private static final int COL_INDEX_STATUS = 3;

	public static List<String> queryUnprocessedExportRequests()
	{
		List<String> unprocessedExportNumbers = new ArrayList<>();
		try
		{
			Class.forName(ExportRequestDbConnector.DB_DRIVER);
			Connection cn = DriverManager.getConnection(
					ExportRequestDbConnector.DB_URL,
					ExportRequestDbConnector.DB_USER,
					ExportRequestDbConnector.DB_PASSWD);
			Statement st = cn.createStatement();
			ResultSet rs = st
					.executeQuery("select * from exportrequest where status = '"
							+ ExportRequestStatus.UNPROCESSED + "';");
			while (rs.next())
			{
				unprocessedExportNumbers.add(rs.getString(COL_INDEX_REQUEST_NUMBER));
			}
			return unprocessedExportNumbers;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static void updateRequestStatus(String requestNumber, ExportRequestStatus status)
	{
		try
		{
			Class.forName(ExportRequestDbConnector.DB_DRIVER);
			Connection cn = DriverManager.getConnection(
					ExportRequestDbConnector.DB_URL,
					ExportRequestDbConnector.DB_USER,
					ExportRequestDbConnector.DB_PASSWD);
			Statement st = cn.createStatement();
			st.executeUpdate("update exportrequest set status = '"+status+"' where requestnumber = '"+requestNumber+"';");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
