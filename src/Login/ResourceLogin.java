/**
 * Class for in line - proceeding to room booking
 * @author stutirastogi
 * @date 11/6/15
 */
package Login;
import Room.*;

import java.sql.SQLException;
import java.util.Scanner;

public class ResourceLogin
{
	protected Scanner scan = new Scanner(System.in);
	int choice;
	RoomResource room1;
	
	/**
	 * Method to continue to room booking
	 * @throws SQLException
	 */
	public ResourceLogin() throws SQLException
	{
		System.out.println("Press 1 to continue to room booking\n\n");
		choice = scan.nextInt();
		room1 = new RoomResource();
	}
}
