/**
 * Class with various functions related to a room
 * @author stutirastogi
 * @date 11/06/15
 */
package Room;

import Login.*;

import java.io.BufferedReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.concurrent.TimeUnit;

import Login.User;
import Main.Resource;

public class RoomResource extends Resource
{
	public String reason, roomno;
	public int roomtype;
	public int bill;
	public String reasondenied;
	public Timestamp startTime, endTime;
	public long appNo;
	
	Scanner scan = new Scanner(System.in);
	
	public RoomResource() throws SQLException
	{	//super();
		
	}
	
	public void Choices(User user) throws SQLException, ParseException
	{
		System.out.println("Make a choice");
		System.out.println("1  -  New Booking");
		System.out.println("2  -  Check Status");
		System.out.println("3  -  Cancellation");
		
		int newch = scan.nextInt();
		switch(newch)
		{
			case 1 : 
				this.newRoom(user);	
				break;
			case 2 : 
				this.check(user);
				break;	
			case 3 : 
				this.cancel(user);	
				break;
		}
	}
	
	public void newRoom(User user) throws SQLException, ParseException
	{	
		RoomResource ro = new RoomResource();
		RoomDB rodb = new RoomDB();
		System.out.println("Enter the details for booking the room");
		System.out.println("\nCheck in\n");
		
		boolean dateformat = true;  //true = wrong data , false = correct data
		while(dateformat)
		{
			System.out.println("Enter the date in the format DD MM YYYY");
			int day = scan.nextInt();
			int mon = scan.nextInt();
			int year = scan.nextInt();
			
			System.out.println("Enter the time in the format HH MM 24-HR format");
			int hr = scan.nextInt();
			int min = scan.nextInt();
			
			Calendar cal = Calendar.getInstance();
	        Calendar calnew = Calendar.getInstance();		// creates calendar
	        
	        cal.setTime(new Date()); 						// sets calendar time/date
	        cal.add(Calendar.DAY_OF_MONTH, 1); 				// adds one day
	        
	        calnew.set(year, mon-1, day, hr, min);
	        
	        if(day>30||day<1) 
	        {
				dateformat=true;
				System.out.println("wrong data...enter again");
				continue;
			}
			else if(mon>12||mon<1) 
			{
				dateformat=true;
				System.out.println("wrong data...enter again");
				continue;
			}
			else if(hr>23||hr<0) 
			{
				dateformat=true;
				System.out.println("wrong data...enter again");
				continue;
			}
			else if(min>59||min<0) 
			{
				dateformat=true;
				System.out.println("wrong data...enter again");
				continue;
			}
			else if(min>0 && hr==23) 
			{
				System.out.println("Taking booking from 23:00 till 23:59 ....maximum possible entry");
			}
			else if(calnew.compareTo(cal)<0)
			{ 	
				//checks whether cal > calnew
				dateformat=true;
				System.out.println("Sorry, but you have to book rooms atleast 1 day earlier....enter again");
			}
			
			else dateformat=false;
	        
	        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss");
	        String parseString = String.valueOf(day) + ":" + String.valueOf(mon) + ":" + String.valueOf(year) + " " + String.valueOf(hr) + ":" + String.valueOf(min) + ":00";
	        Date date = dateFormat.parse(parseString);
	        long time = date.getTime();
	        ro.startTime = new Timestamp(time);
		}
		  
		System.out.println("\nCheck out\n");
		
		dateformat = true;  //true = wrong data , false = correct data
		while(dateformat)
		{
			System.out.println("Enter the date in the format DD MM YYYY");
			int day = scan.nextInt();
			int mon = scan.nextInt();
			int year = scan.nextInt();
			
			System.out.println("Enter the time in the format HH MM 24-HR format");
			int hr = scan.nextInt();
			int min = scan.nextInt();
			
			Calendar cal = Calendar.getInstance();
	        Calendar calnew = Calendar.getInstance();		// creates calendar
	        
	        cal.setTime(new Date()); 						// sets calendar time/date
	        cal.add(Calendar.DAY_OF_MONTH, 1); 				// adds one day
	        
	        calnew.set(year, mon-1, day, hr, min);
	        
	        if(day>30||day<1) 
	        {
				dateformat=true;
				System.out.println("wrong data...enter again");
				continue;
			}
			else if(mon>12||mon<1) 
			{
				dateformat=true;
				System.out.println("wrong data...enter again");
				continue;
			}
			else if(hr>23||hr<0) 
			{
				dateformat=true;
				System.out.println("wrong data...enter again");
				continue;
			}
			else if(min>59||min<0) 
			{
				dateformat=true;
				System.out.println("wrong data...enter again");
				continue;
			}
			else if(min>0 && hr==23) 
			{
				System.out.println("Taking booking from 23:00 till 23:59 ....maximum possible entry");
			}
			else if(calnew.compareTo(cal)<0)
			{ 	
				//checks whether cal > calnew
				dateformat=true;
				System.out.println("Sorry, but you have to book rooms atleast 1 day earlier....enter again");
			}
			
			else dateformat=false;
	        
	        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss");
	        String parseString = String.valueOf(day) + ":" + String.valueOf(mon) + ":" + String.valueOf(year) + " " + String.valueOf(hr) + ":" + String.valueOf(min) + ":00";
	        Date date = dateFormat.parse(parseString);
	        long time = date.getTime();
	        ro.endTime = new Timestamp(time);
		}
		
		Long diff = ro.endTime.getTime() - ro.startTime.getTime();
		int units = 0;
			
		System.out.println("Select a room type");	
		System.out.println("1  -  Deluxe room");
		System.out.println("2  -  Suite");
		System.out.println("3  -  Conference room");
		System.out.println("4  -  Banquet Hall");
		
		int newchoice = scan.nextInt();
		ro.roomtype = newchoice;
		if (ro.roomtype > 2)
		{
			//per hour basis
			units = (int)TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
		}
		else
		{
			//per day basis
			units = (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		}
		
		Dues dues = new Dues();
		ro.bill = dues.calculate(user, ro.roomtype, units);
		
		String resultAvail = rodb.roomCheck(ro);	
		
		if(resultAvail.matches("problem"))
		{
			System.out.println("Sorry. No rooms available at the given time.");
			return;
		}
		else
		{
			ro.roomno = resultAvail;
			
			System.out.println("Enter the reason for room requirement (conference/banquet)");
			ro.reason = scan.next()+scan.nextLine();
			
			rodb.queue(ro, user);
			
			System.out.println("Your request has been queued");
			System.out.println("You can check status of your application later"+"\n");	
		}
	}
	
	public String check(User user) throws SQLException
	{	
		RoomDB rodb = new RoomDB();
		
		String junk = rodb.checkDB(user);
		if(junk.matches("0")) 
			System.out.println("You have no pending applications now");
		return junk;
	}
	
	public void cancel(User user) throws SQLException
	{	
		Dues dues = new Dues();
		RoomResource ro = new RoomResource();
		RoomDB rodb = new RoomDB();
		
		System.out.println("Status of your applications");
		String junk = check(user);
		
		if(junk.matches("0")) 
		{
			return;
		}
		
		else
		{
			System.out.println("Enter the Application No. for which you want to cancel application");
			System.out.println("You can also press 0 to return");
			
			int AppNo = scan.nextInt();
			if(AppNo==0) 
				return;
			
			rodb.cancelDB(AppNo, "", user);
			dues.refund(user, AppNo);
		}	
	}	
}