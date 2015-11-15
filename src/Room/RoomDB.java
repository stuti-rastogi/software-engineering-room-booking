/**
 * Class to check availability, queue room requests
 * @author stutirastogi
 * @date 11/7/15
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

    /**
     * Method to check availability of room
     * @param ro The RoomResource object having roomtype
     * @return problem if not available, and room no. if available
     * @throws SQLException
     */
	public String roomCheck(RoomResource ro) throws SQLException
	{	
		String type;
		ConnectRoom crc = new ConnectRoom();
		
		String details = "SELECT * FROM `rooms` WHERE `RoomType` = '" + ro.roomtype + "'";
		String result = crc.Search(details, 1, ro); 	//operation 1 for checking availability
        return result;
	}
	
	/**
	 * Method to handle booking request
	 * @param ro The roomresource object associated with booking
	 * @param user The user object associated with booking
	 * @throws SQLException
	 */
	public void queue(RoomResource ro, User user) throws SQLException
	{	
		Dues dues = new Dues();
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
			ro.bill = dues.calculate(user, ro.roomtype, ro.duration);
			dues.collect(user, ro.bill);		//update due amount of guest
			
			details = "INSERT INTO `hotel`.`bookings` (`RoomNo`, `RoomType`, `GuestName`, `GuestID`, `Start`, `End`, `Reason`, `Bill`, `Granted`, `Email`) VALUES ('"+ro.roomno+"', '"+ro.roomtype+"', '"+user.name+"', '"+user.id+"', '"+ro.startTime+"', '"+ro.endTime+"', '"+ro.reason+"', '"+ro.bill+"', '1', '"+user.contact+"');";
			change = "UPDATE `hotel`.`rooms` SET `isBooked` = '1' WHERE `rooms`.`RoomNo` = '"+ro.roomno+"'";

			cr.Connection(details);
			cr.Connection(change);

		}
	}

	/**
	 * Method that checks bookings of a user
	 * @param user The user object whose bookings need to be fetched
	 * @return 0 if no entries
	 * @throws SQLException
	 */
	public String checkDB(User user) throws SQLException
	{	
		RoomResource ro = new RoomResource();
		ConnectRoom crc = new ConnectRoom();
		String details = "SELECT * FROM `bookings` WHERE `GuestName` = '" + user.name + "'";
		String junk = crc.Search(details, 2, ro);
    	
    	return junk;
	}
	
	/**
	 * Method to handle cancellation of bookings
	 * @param AppNo The application no. of booking to be cancelled
	 * @param roomno The room no. allotted to be freed
	 * @param user The user associated with cancelling
	 * @throws SQLException
	 */
	public void cancelDB(int AppNo, String roomno, User user) throws SQLException
	{	
		Dues dues = new Dues();
		dues.refund(user, AppNo);
		ConnectRoom cr = new ConnectRoom();
		
		String details = "DELETE FROM `hotel`.`bookings` WHERE `bookings`.`AppNo` = "+ AppNo;
		cr.Connection(details);
		
		String order = "UPDATE `hotel`.`rooms` SET `isBooked` = '0' WHERE `rooms`.`RoomNo` = '" + roomno + "';";
		cr.Connection(order);
	}
	
	/**
	 * Method to handle admin actions of approving/denying requests
	 * @throws SQLException
	 */
	public void decide() throws SQLException
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