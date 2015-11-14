/**
 * Class dealing with the room table of the database
 * @author stutirastogi
 * @date 11/06/15
 */
package Room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Email.SendEmail;

public class ConnectRoom 
{
	public void Connection(String sql)
	{
			
			// JDBC driver name and database URL
		   	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		   	final String DB_URL = "jdbc:mysql://localhost:3306/hotel";

		   	//  Database credentials
		   	final String USER = "root";
		   	final String PASS = "";
		   
		   	//public static void main(String[] args) {
		   	Connection conn = null;
		   	Statement stmt = null;
		   
		   	try
		   	{
		      	Class.forName(JDBC_DRIVER);
		      	conn = DriverManager.getConnection(DB_URL, USER, PASS);

		      	stmt = conn.createStatement();
		      	stmt.executeUpdate(sql);
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
		      	}//end finally try
		   }//end try		   
	}
	
	public String Search(String sql, int operation, RoomResource ro)
	{ 
		//operation 1 for checking availability
		//operation 2 used for room admin
		//operation 3 to send mail
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/hotel";

		//  Database credentials
		final String USER = "root";
		final String PASS = "";
		   
		Connection conn = null;
		Statement stmt = null;
		long AppNo = 0;
		
		
		try
		{
			if(operation==1)
			{
				//check availability
				Class.forName(JDBC_DRIVER);

				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();
			    
			    ResultSet rs = stmt.executeQuery(sql);
			    ArrayList<String> roomsToCheck = new ArrayList<String>();
			    
			    while(rs.next())
			    {
			    	int isBooked = rs.getInt("isBooked");
			   		String roomno  = rs.getString("RoomNo");
			   		
			   		if (isBooked == 0)
			   			return roomno;
		        	else
		        		roomsToCheck.add(roomno);
			    }
			    
			    for (String roomno : roomsToCheck)
			    {
			    	String query = "SELECT * FROM `bookings` WHERE `RoomNo` = '" + roomno + "' AND `Granted` = 1";
		        	Statement stmtQuery = conn.createStatement();
		        	ResultSet rsBookings = stmtQuery.executeQuery(query);
		        	
		        	while(rsBookings.next())
				    {
				    	Timestamp checkin = rsBookings.getTimestamp("Start");
				    	Timestamp checkout = rsBookings.getTimestamp("End");
				    	
				    	if (ro.startTime.after(checkin) && ro.startTime.before(checkin))
				    		continue;
				    	else if (ro.startTime.before(checkin) && (ro.endTime.after(checkin) && ro.endTime.before(checkout)))
				    		continue;
				    	else
				    		return roomno;
				    }
		        	//none could be returned out of the booked ones - all have a clash
		        	return "problem";
			    } 
			}
			
			else if (operation == 2)
			{
				//admin
				Class.forName("com.mysql.jdbc.Driver");

				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();
			    
			    ResultSet rs = stmt.executeQuery(sql);
			    
			    while (rs.next())
			    {
			    	int granted = rs.getInt("Granted");
				    String reasondenied = rs.getString("ReasonDenied");
				    String roomno  = rs.getString("RoomNo");
				    AppNo = rs.getLong("AppNo");
				    
					//room admin
					if(granted == 0)
		        	{
		        		System.out.println("Application No. "+AppNo+" Room "+roomno+" is queued");
		        	}
		        	else if(granted == 1)
		        	{
		        		System.out.println("Application No. "+AppNo+" Room "+roomno+" is  booked");
		        	}
		        	else 
		        		System.out.println("Permission for Room "+roomno+" was denied because "+reasondenied);
		        	continue;
			    }  
			}
			
			else if (operation == 3)
			{
				//send mail
				Class.forName("com.mysql.jdbc.Driver");

				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();
			    
			    ResultSet rs = stmt.executeQuery(sql);
			    
			    while (rs.next())
			    {
			    	int granted = rs.getInt("Granted");
				    String reasondenied = rs.getString("ReasonDenied");
				    String roomtype  = rs.getString("RoomType");
				    String guestName = rs.getString("GuestName");
				    String email = rs.getString("Email");
				    Timestamp start = rs.getTimestamp("Start");
				    Timestamp end = rs.getTimestamp("End");
				    AppNo = rs.getLong("AppNo");
				    
					//send mail
					String body="Your application for " + roomtype + " has been ";
		        	String head = "Update on Room booking Application for Guest "+ guestName;
		        	
		        	if(granted==1)
		        	{
		        		body = body+"approved for "+start.toString()+"-"+end.toString();
		        	}
		        	else 
		        		body = body+"denied because "+reasondenied+"\n"+" Details were "+start.toString()+"-"+end.toString(); 
		        	
		        	SendEmail se = new SendEmail();
		        	se.mail(email, body, head);
			    }
			}
		}
			
		catch(SQLException se)
		{
			//Handle errors for JDBC
		 	se.printStackTrace(); //return "no problem";
		}

		catch(Exception e)
		{
			//Handle errors for Class.forName
			e.printStackTrace();//return "no problem";
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
		    }//end finally try
		}//end try
		   	  
		if(AppNo==0)
		{
			return "0";
		}
		else
		{
			return "no problem";
		}	   
	}
	
	
	public String[] getOldEntries(String sql)
	{ 
		//operation 3 to send mail and operation 1 for checking availability
		
		int count=0;
		String[] results = new String[100];
		
		// JDBC driver name and database URL
		
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/hotel";

		//  Database credentials
		final String USER = "root";
		final String PASS = "";
		 
		Connection conn = null;
		Statement stmt = null;
		long AppNo = 0;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

		    ResultSet rs = stmt.executeQuery(sql);
		      
			while(rs.next())
			{
		    	int granted = rs.getInt("Granted");
			    String reasondenied = rs.getString("ReasonDenied");
			    String roomtype  = rs.getString("RoomType");
			    String roomno  = rs.getString("RoomNo");
			    Timestamp start = rs.getTimestamp("Start");
			    Timestamp end = rs.getTimestamp("End");
			    AppNo = rs.getLong("AppNo");
			    String email = rs.getString("Email");
		        //Retrieved by column name
		          
		        if(granted==0)
		        {
		        	results[count]=("Application No. " + AppNo + ", Room " + roomno + " is queued");
		        }
		        else if(granted==1)
		        {
		        	results[count]=("Application No. " + AppNo + ", Room " + roomno + " is  booked");
		        }
		        else 
		        	results[count]=("Application No. "+AppNo+" for Room "+roomno+" was denied because "+reasondenied);
		        
		        count+=1;
		        continue;
		        
		       	        
		    }
		    
		    if(count==0)
		    {
	    		System.out.println("You do not have any bookings at this time"); return results;
	    	}
		    
		    rs.close();
		}

		catch(SQLException se)
		{
		    //Handle errors for JDBC
		    se.printStackTrace(); //return "no problem";
		}

		catch(Exception e)
		{
		    //Handle errors for Class.forName
		    e.printStackTrace();//return "no problem";
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
			}//end finally try
		}//end try
		return results;	   
	}
	
	public String getDate(String sql)
	{   	
		//0 = update, 1 = execute
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
	    	Class.forName(JDBC_DRIVER);
	      	conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      	stmt = conn.createStatement();
	      
	       	long AppNo=0;
	    	ResultSet rs = stmt.executeQuery(sql);
	    	  
	    	while(rs.next())
	    	{
				//Retrieve by column name

				Timestamp start = rs.getTimestamp("Start");
				Timestamp end = rs.getTimestamp("End");
			    
			    AppNo = rs.getLong("AppNo");

			    String startStr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(start);
			    String endStr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(end);
			    return startStr;
	    	} 	
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
	      	}//end finally try
	   	}//end try
		return result;  
	}
	
	public String getreason(String sql)
	{ 
		//operation 3 to send mail and operation 1 for checking availability
		int count=0;
		String[] results = new String[100];
		
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/oop";

		//  Database credentials
		final String USER = "root";
		final String PASS = "";
		   
		Connection conn = null;
		Statement stmt = null;
		long AppNo = 0;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

		    stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery(sql);
		      
		    while(rs.next())
		    {
		        //Retrieve by column name
		    	String reasondenied = rs.getString("Reason denied");
		        AppNo = rs.getLong("AppNo");
		        String reason = rs.getString("Reason");
		        return reason;  	        
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
		    }//end finally try
		}//end try
		return "no";	   
	}
	
	public int costCalc(String sql, int roomType, int operation)
	{ 
		//0 = for total calculation and 
		//1 = getting loan amount and 
		//2 = getting amount to be refunded
		//String result="";
		// JDBC driver name and database URL
		
		int cost = 0;
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/hotel";

		//  Database credentials
		final String USER = "root";
		final String PASS = "";
		   
		Connection conn = null;
		Statement stmt = null;
		   
		try
		{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				//Retrieve by column name
			    if(operation == 0)
			    	cost = rs.getInt("UnitCost");
			    else if(operation == 1)
			    	cost = rs.getInt("SUM(Amount)");
			    else 
			    	cost = rs.getInt("Bill");
			        return cost;
			}
			      
			rs.close();//return "no problem";
		}
		      
		catch(SQLException se)
		{
			//Handle errors for JDBC
		    se.printStackTrace(); //return "no problem";
		}
		
		catch(Exception e)
		{
			//Handle errors for Class.forName
		    e.printStackTrace();//return "no problem";
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
		    }//end finally try
		}//end try
		
		return cost;	   
	}
	
	public int getdues(String sql)
	{ 
		//operation 3 to send mail and operation 1 for checking availability
		int count=0;
		String[] results = new String[100];
		
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/oop";

		//  Database credentials
		final String USER = "root";
		final String PASS = "";
		   
		Connection conn = null;
		Statement stmt = null;
		long AppNo = 0;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

		    stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery(sql);
		      
		    while(rs.next())
		    {
		         int due = rs.getInt("Amount");
		         return due;
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
		    }//end finally try
		}//end try	
		return 0;
	}
}