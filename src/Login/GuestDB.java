/**
 * A class to create user object
 * @author stutirastogi
 * @date 11/6/15
 */
package Login;

public class GuestDB
{
	String xusr, xpwd;
	String dbname, dbid, dbcontact;
	public Boolean adminlogin = null;
	
	public GuestDB(LoginDetails lgn)
	{	

	}
	
	public User isVerified()
	{
		//all user fields set
		User user = new User();
		user.name = dbname;
		user.id = dbid;
		user.contact = dbcontact;
		user.usr = xusr;
		user.pwd = xpwd;
		
		if(adminlogin == true)
			user.admin = true;
		else if(adminlogin == false)
			user.admin = false;
		
		return user;
	}
}