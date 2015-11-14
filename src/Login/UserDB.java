package Login;
	
import java.sql.*;	

public class UserDB 
{
	String xusr, xpwd, xcontact;
	String dbname, dbid;
	public Boolean adminlogin = false;
    Connection con;
    Statement stmt;
    int cnt;

	
	public String UserDBVerify(String usr, String pwd) throws SQLException
	{	
		Connect conn = new Connect();
		String sql = "SELECT * FROM `user` WHERE `Username` = '"+usr+"' AND `Password` = '"+pwd+"'";
		String result = conn.Connection(sql);

		if(result.matches("N"))
		{
			return "Unsuccessful";
		}
		else
		{
			xusr=usr;
			xpwd=pwd;
			
			String contactlength = result.substring(1, 3);
			int conlength = Integer.parseInt(contactlength);
			
			dbid = result.substring(3, 15);							//ID
			xcontact = result.substring(15, 15 + conlength);		//contact
			dbname = result.substring(15 + conlength);				//name
			
			String adminResult = result.substring(0,1);					//admin login check
			
			if(adminResult.matches("1")) 
				adminlogin = true;
			
			return adminResult+"Successful";
		}
	}
	
	public User isVerified()
	{
		User user = new User();
		user.name = this.dbname;
		
		if(user.name==null)
			System.exit(0);
		
		user.id = dbid;
		user.contact=xcontact;
		user.usr = xusr;
		user.pwd = xpwd;
		
		if(adminlogin == true)
			user.admin = true;
		else if(adminlogin == false)
			user.admin = false;
		
		return user;
	}
}	