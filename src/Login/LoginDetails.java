/**
 * Class for login scanning in line
 * @author stutirastogi
 * @date 11/6/15
 */
package Login;
import java.util.Scanner;

public class LoginDetails
{
	protected String usr, pwd;
	Scanner scan;
	
	/**
	 * A method to input username and password
	 */
	public LoginDetails()
	{
		scan = new Scanner(System.in);
		System.out.println("Enter Login Username");
		
		usr = scan.next();
		System.out.println("Enter Login Password");
		
		pwd = scan.next();
	}
}
