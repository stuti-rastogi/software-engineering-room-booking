package Login;
import Room.*;

import java.sql.SQLException;
import java.util.Scanner;

public class ResourceLogin
{
	protected Scanner scan = new Scanner(System.in);
	int choice;
	RoomResource room1;
	
	public ResourceLogin() throws SQLException
	{
		System.out.println("Press 1 to continue to room booking\n\n");
		
		choice = scan.nextInt();
		
		room1 = new RoomResource();
	}
}
