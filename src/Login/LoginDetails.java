package Login;
import java.util.Scanner;

public class LoginDetails
{
	protected String usr, pwd;
	Scanner scan;
	
	public LoginDetails()
	{
		scan = new Scanner(System.in);
		System.out.println("Enter Login Username");
		
		usr = scan.next();
		System.out.println("Enter Login Password");
		
		pwd = scan.next();
	}
}
