/**
 * Class for admin to manage room
 * @author stutirastogi
 */
package Room;
import java.sql.SQLException;

import Login.*;
import Main.Resource;
import Login.User;
import Main.Resource;

public class RoomA extends Resource
{
	public RoomA() throws SQLException
	{	
		super();
	}

	public void admin(User user) throws SQLException 
	{
		System.out.println("Welcome "+ user.name);
		RoomResource ro = new RoomResource();
		RoomDB rodb = new RoomDB();
		rodb.decide(user);		
	}
}