/**
 * 
 */
package Mousedatabase;

import java.sql.*;

/**
 * @author evagita
 *
 */
public class MouseDataBase {
	private Statement stmt;
	private Connection conn;
	public Boolean connected = false;
	public double Error = 1.7E+308;
	/**
	 * @param args
	 */
	MouseDataBase(String curs, String user, String password) 
	{
		// TODO Auto-generated method stub
		try
		{
			conn = DriverManager.getConnection(curs,user,password);
			
		if (conn == null)
		{
			// No connected to DB
			connected = false;
		}
		stmt = conn.createStatement();
		connected = true;
		} catch (SQLException ex)
		{
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	//To see, how many money user have
	public double SeeKredit(String user, String password) throws SQLException
		{
			if (connected)
			{
				ResultSet rs = stmt.executeQuery("SELECT money FROM ACCOUNTS WHERE name = '"+user+"' " +
												 " AND password = '"+password+"'");
				
				while (rs.next()) 
				{
					return rs.getDouble(1);
				}
			}
			return Error;
		}
	private String SeePassword(String user) throws SQLException
	{
		String Password = null;
		if (connected)
		{
			ResultSet rs = stmt.executeQuery("SELECT password FROM ACCOUNTS WHERE name = '"+user+"'");
			
			while (rs.next())
			{
				Password = rs.getString(1);
			}
		}
		return Password;
	}
	//To send money to other user
	public Boolean SendMoney(String FromWho, String Password, String ToWho, double Money) throws SQLException
	{
		if (connected)
		{
			if(SeeKredit(FromWho,Password)-Money>0)
			{
				if (stmt.executeUpdate("UPDATE ACCOUNTS SET money="+(SeeKredit(FromWho,Password)-Money)+
										" WHERE name = '"+FromWho+"'") == 1)
				{
					if (stmt.executeUpdate("UPDATE ACCOUNTS SET money="+(SeeKredit(ToWho,SeePassword(ToWho))+Money)+
							" WHERE name = '"+ToWho+"'") == 1)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//To create new account 
	public Boolean CreateNewAcc(String NameUser, String Password, double money) throws SQLException
	{
		if (connected)
		{
			if(money>0)
			{
				if (stmt.executeUpdate("INSERT ACCOUNTS VALUES ('"+NameUser+"','"+Password+"','"+money+"')") == 1)
				{
					return true;
				}
			}	
		}
		return false;
	}
	
	//To delete account
	public Boolean DeleteAcc(String User, String Password) throws SQLException
	{
		if (connected)
		{
			if (stmt.executeUpdate("DELETE FROM ACCOUNTS WHERE name = '"+User+"' AND password='"+Password+"'") == 1)
			{
				return true;
			}
		}
			return false;
	}
	//
}
