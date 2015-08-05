package de.gravitex.bpmn.server.test;

import java.io.*;
import java.sql.*;

public class DbTableShow
{
	public static void main(String[] args)
	{
		showDbTable("customers", "org.postgresql.Driver", "jdbc:postgresql://localhost/postgres", "postgres",
				"pgvedder");
	}

	static void showDbTable(String dbTbl, String dbDrv, String dbUrl,
			String dbUsr, String dbPwd)
	{
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		try
		{
			// Select fitting database driver and connect:
			Class.forName(dbDrv);
			cn = DriverManager.getConnection(dbUrl, dbUsr, dbPwd);
			st = cn.createStatement();
			rs = st.executeQuery("select * from " + dbTbl);
			// Get meta data:
			ResultSetMetaData rsmd = rs.getMetaData();
			int i, n = rsmd.getColumnCount();
			// Print table content:
			for (i = 0; i < n; i++)
				System.out.print("+---------------");
			System.out.println("+");
			for (i = 1; i <= n; i++)
				// Attention: first column with 1 instead of 0
				System.out
						.print("| " + extendStringTo14(rsmd.getColumnName(i)));
			System.out.println("|");
			for (i = 0; i < n; i++)
				System.out.print("+---------------");
			System.out.println("+");
			while (rs.next())
			{
				for (i = 1; i <= n; i++)
					// Attention: first column with 1 instead of 0
					System.out.print("| " + extendStringTo14(rs.getString(i)));
				System.out.println("|");
			}
			for (i = 0; i < n; i++)
				System.out.print("+---------------");
			System.out.println("+");
		} catch (Exception ex)
		{
			System.out.println(ex);
		} finally
		{
			try
			{
				if (rs != null)
					rs.close();
			} catch (Exception ex)
			{/* nothing to do */
			}
			try
			{
				if (st != null)
					st.close();
			} catch (Exception ex)
			{/* nothing to do */
			}
			try
			{
				if (cn != null)
					cn.close();
			} catch (Exception ex)
			{/* nothing to do */
			}
		}
	}

	// Extend String to length of 14 characters
	static final String extendStringTo14(String s)
	{
		if (s == null)
		{
			s = "";
		}
		final String sFillStrWithWantLen = "              ";
		final int iWantLen = sFillStrWithWantLen.length();
		final int iActLen = s.length();
		if (iActLen < iWantLen)
			return (s + sFillStrWithWantLen).substring(0, iWantLen);
		if (iActLen > 2 * iWantLen)
			return s.substring(0, 2 * iWantLen);
		return s;
	}
}