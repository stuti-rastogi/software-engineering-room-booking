package Room;

import Login.User;

public class Dues
{
	public int calculate(User user, int roomType, int duration)
	{
		ConnectRoom cr = new ConnectRoom();
		String sql = "SELECT * FROM `tariff` WHERE `RoomType` = '" + Integer.toString(roomType) + "'";
		int calculated = cr.costCalc(sql, roomType, 0);
		
		int total = calculated * duration * 2 / 5;			//40% advance
		return total;
	}
	
	void collect(User user,int bill)
	{	
		int dues = 0;
		String add = "SELECT SUM(Amount) FROM `hotel`.`user` WHERE `ID` = '"+user.id+"'";
		
		ConnectRoom cr = new ConnectRoom();
		
		dues = cr.costCalc(add, 0, 1);	//returns present account balance
		System.out.println("Current: " + dues);
		dues = dues + bill;
		
		String amt = "UPDATE `hotel`.`user` SET `Amount` = '" + dues + "' WHERE `ID` = '" + user.id + "' AND `user`.`Name` = '" + user.name + "'";
		cr.Connection(amt);
	}
	
	public void refund(User user, int appno)
	{
		ConnectRoom cr = new ConnectRoom();
		
		String amt = "SELECT * FROM `hotel`.`bookings` WHERE `bookings`.`AppNo` = " + appno;
		int amtr = cr.costCalc(amt, 0, 2);
		System.out.println(amt);
		System.out.println("Amount bill: " + amtr);
		
		String add = "SELECT SUM(Amount) FROM `hotel`.`user` WHERE `ID` = '" + user.id + "'";
		int dues = cr.costCalc(add, 0, 1);	//returns present account balance
		System.out.println("Present: " + dues);
		
		dues = dues - amtr;
		String latest = "UPDATE `hotel`.`user` SET `Amount` = '" + dues + "' WHERE `ID` = '" + user.id + "' AND `user`.`Name` = '" + user.name + "'";
		cr.Connection(latest);
	}
}
