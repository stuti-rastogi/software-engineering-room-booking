/**
 * Class to check availability, queue room requests
 * @author stutirastogi
 */
package Room;
import java.sql.*;
import java.util.Scanner;

import Login.User;

public class RoomDB //extends Room
{	
	Scanner scan = new Scanner(System.in);
    Connection con;
    Statement stmt;
    int cnt;
    
    public RoomDB() throws SQLException
    {

    }

	public String roomCheck(RoomResource ro) throws SQLException
	{	
		String type;
		ConnectRoom crc = new ConnectRoom();
		
		String details = "SELECT * FROM `rooms` WHERE `RoomType` = '" + ro.roomtype + "'";
		String result = crc.Search(details, 1, ro); //operation 1 for checking availability
        System.out.println(result);
        return result;
	}
	
	public void queue(RoomResource ro, User user) throws SQLException
	{	
		ConnectRoom cr = new ConnectRoom();
		String details, change;
		if (ro.roomtype > 2)
		{
			//conference or banquet - queue
			details = "INSERT INTO `hotel`.`bookings` (`RoomNo`, `RoomType`, `GuestName`, `GuestID`, `Start`, `End`, `Reason`, `Granted`, `Email`) VALUES ('"+ro.roomno+"', '"+ro.roomtype+"', '"+user.name+"', '"+user.id+"', '"+ro.startTime+"', '"+ro.endTime+"', '"+ro.reason+"', '0', '"+user.contact+"');";
			cr.Connection(details);
		}
		else
		{
			//rooms are immediately granted
			details = "INSERT INTO `hotel`.`bookings` (`RoomNo`, `RoomType`, `GuestName`, `GuestID`, `Start`, `End`, `Reason`, `Granted`, `Email`) VALUES ('"+ro.roomno+"', '"+ro.roomtype+"', '"+user.name+"', '"+user.id+"', '"+ro.startTime+"', '"+ro.endTime+"', '"+ro.reason+"', '1', '"+user.contact+"');";
			change = "UPDATE `hotel`.`rooms` SET `isBooked` = '1' WHERE `rooms`.`RoomNo` = '"+ro.roomno+"'";
			cr.Connection(details);
			cr.Connection(change);
		}	
	}

	public String checkDB(User user) throws SQLException
	{	
		RoomResource ro = new RoomResource();
		ConnectRoom crc = new ConnectRoom();
		String details = "SELECT * FROM `bookings` WHERE `GuestName` = '" + user.name + "'";
		String junk = crc.Search(details, 2, ro);
    	
    	return junk;
	}
	
	public void cancelDB(int AppNo,User user) throws SQLException
	{	
		ConnectRoom cr = new ConnectRoom();
		String details = "DELETE FROM `oop`.`room` WHERE `room`.`AppNo` = "+AppNo;
		cr.Connection(details);
	}
	
	public void decide(User user) throws SQLException
	{	
		String contin = "y";
		while(contin.matches("y"))
		{
			RoomResource ro = new RoomResource();
			ConnectRoom crc = new ConnectRoom();
			
			//conference room
			String details = "SELECT * FROM `bookings` WHERE `Granted` = '0' and `RoomType` = '3'";
			String junk = crc.Search(details, 2, ro);
			
			if(junk.matches("0"))
			{
				System.out.println("No applications now");
				return;
			}
			
			else
			{
				System.out.println("Enter the Application No. for which you want to accept/deny or press 0 to return");
				ro.appNo = scan.nextLong();
				
				if(ro.appNo==0)
				{
					return;
				}
				else
				{
					System.out.println("Enter 1 to accept and 2 to deny");
					long ans = scan.nextLong();
					
					if(ans==2)
					{
						System.out.println("Enter reason for denying");
						ro.reasondenied = scan.next() + scan.nextLine();
					}

					String order = "UPDATE `hotel`.`bookings` SET `Granted` = '"+ans+"', `ReasonDenied` = '"+ro.reasondenied+"' WHERE `bookings`.`AppNo` = "+ro.appNo;
					crc.Connection(order);
					if (ans == 1)
					{
						String order2 = "UPDATE `hotel`.`rooms` SET `isBooked` = '1' WHERE `rooms`.`RoomType` = '3'";
						crc.Connection(order2);
					}
					
					String queryEmail = "SELECT * FROM `bookings` WHERE `AppNo` = "+ro.appNo;
					crc.Search(queryEmail, 3, ro);
					
					System.out.println("Do you want to continue (y/n)");
					contin = scan.next();
					contin.toLowerCase();	
				}
			}
			
			//banquet hall
			details = "SELECT * FROM `bookings` WHERE `Granted` = '0' and `RoomType` = '4'";
			junk = crc.Search(details, 2, ro);
			
			if(junk.matches("0"))
			{
				System.out.println("No applications now");
				return;
			}
			
			else
			{
				System.out.println("Enter the Application No. for which you want to accept/deny or press 0 to return");
				ro.appNo = scan.nextLong();
				
				if(ro.appNo==0)
				{
					return;
				}
				else
				{
					System.out.println("Enter 1 to accept and 2 to deny");
					long ans = scan.nextLong();
					
					if(ans==2)
					{
						System.out.println("Enter reason for denying");
						ro.reasondenied = scan.next() + scan.nextLine();
					}

					String order = "UPDATE `hotel`.`bookings` SET `Granted` = '"+ans+"', `ReasonDenied` = '"+ro.reasondenied+"' WHERE `bookings`.`AppNo` = "+ro.appNo;
					crc.Connection(order);
					if (ans == 1)
					{
						String order2 = "UPDATE `hotel`.`rooms` SET `isBooked` = '1' WHERE `rooms`.`RoomType` = '4'";
						crc.Connection(order2);
					}
					
					String queryEmail = "SELECT * FROM `bookings` WHERE `AppNo` = "+ro.appNo;
					crc.Search(queryEmail, 3, ro);
					
					System.out.println("Do you want to continue (y/n)");
					contin = scan.next();
					contin.toLowerCase();	
				}
			}
		}	
	}
}