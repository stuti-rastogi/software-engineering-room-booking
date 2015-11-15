/**
 * Class to check connection of user
 * @author stutirastogi
 * @date 11/6/15
 */
package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect 
{
	/**
	 * A method to connect to db and fetch user details
	 * @param sql the query to be executed
	 * @return a string returning login details
	 */
	public String Connection(String sql)
	{
			String result="";
			
			// JDBC driver name and database URL
		   	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		   	final String DB_URL = "jdbc:mysql://localhost:3306/hotel";

		   	//  Database credentials
		   	final String USER = "root";
		   	final String PASS = "";
		   
		   	Connection conn = null;
		   	Statement stmt = null;
		   
		   	try
		   	{
		   		//Register JDBC driver
		      	Class.forName(JDBC_DRIVER);

		      	//Open a connection
		      	conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      
		      	//Execute a query
		      	stmt = conn.createStatement();

		      	ResultSet rs = stmt.executeQuery(sql);
		      	//Extract data from result set
		      
		      	while(rs.next())
		      	{
		        	//Retrieve by column name
		         	String id  = rs.getString("ID");
		         	int admin = rs.getInt("IsAdmin");		//0 - no, 1 - yes
		         	String name = rs.getString("Name");
		         	String password = rs.getString("Password");
		         	String contact = rs.getString("Contact");
		         	
		         	int contactlength = 0;
		         	contactlength = contact.length();
		         	
		         	String dummy="";
		         	
		         	//need contact length to be 2 character long
		         	if(contactlength < 10) 
		         		dummy = "0"+Integer.toString(contactlength);
		         	else 
		         		dummy = Integer.toString(contactlength);
		         	
		         	result = admin+dummy+id+password+contact+name;		//string to be returned with all details
		      	}
		      	
		      	if(result=="")
		      	{
		      		//no results from query
		    	 	result = "N";
		      	}
		      	
		      	rs.close();
		   	}

		   	catch(SQLException se)
		   	{
		      	//Handle errors for JDBC
		      	se.printStackTrace();
		   	}

		   	catch(Exception e)
		   	{
		      	//Handle errors for Class.forName
		      	e.printStackTrace();
		   	}

		   	finally
		   	{
		      	//finally block used to close resources
		      	try
		      	{
		       		if(stmt!=null)
		            	conn.close();
		      	}

		      	catch(SQLException se)
		      	{
		      		// do nothing
		      	}
		      	
		      	try
		      	{
		         	if(conn!=null)
		            	conn.close();
		      	}

		      	catch(SQLException se)
		      	{
		        	se.printStackTrace();
		      	}
		   }
		
		return result;	   
	}
}
