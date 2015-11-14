package Main;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import Login.*;

public class MainSystem
{	
	String usr,pwd;
	UserDB udb;
	User user;
	Resource res;
	
	public MainSystem() throws ClassNotFoundException, SQLException, ParseException
	{	
		Scanner scan = new Scanner(System.in);
		String success = "Unsuccessful";
		
		while(success.matches("Unsuccessful"))
		{
			udb = new UserDB();
			success = udb.UserDBVerify(usr,pwd);
			if(success.matches("Unsuccessful"))
				System.out.println("Wrong login details"+"\n"+"Login Again....");
		}
		
		String choice="y";	
		user = udb.isVerified();
		
		while(choice.matches("y"))
		{
			res = new Resource();
			res.ResourceInp(user);
			
			System.out.println("Do you want to do another activity?? ");
			System.out.println("Press 'y' to continue else 'n' to exit");
			
			choice=scan.next();
			choice.toLowerCase();
			
			if(choice.matches("n")) 
			{
				System.out.println("Thanks for visiting us...");
				System.out.println("Exiting.......");
			}
			
		}	
	}
}