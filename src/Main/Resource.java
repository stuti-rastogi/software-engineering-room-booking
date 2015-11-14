package Main;

import Login.*;
import Room.*;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.sql.Timestamp;

public class Resource
{
	protected Scanner scan = new Scanner(System.in);
	int choice;
	RoomResource roomU;
	RoomA roomA;

	protected boolean avail;
	protected int newch;
	public Timestamp start;
	public Timestamp end;
	public int duration;
	public long AppNo;
	public Resource()
	{
		
	}
	
	public void ResourceInp(User user) throws SQLException, ParseException
	{	
		System.out.println("Welcome "+user.name);
		System.out.println("Press:");
		System.out.println("1  -  Room Booking");
		System.out.println("2  -  Exit");
		choice = scan.nextInt();
		
		System.out.println("\n\n");
		
		if(choice == 1)
		{	//System.out.println(user.admin);
			if(user.admin == true)
			{
				roomA = new RoomA();
				roomA.admin(user);
			}
			else
			{//System.out.println(user.admin);
				roomU = new RoomResource();
				roomU.Choices(user);
			}
		}
		else return;
	}
}