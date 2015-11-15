/**
 * Class for payment handling of bookings
 * @author stutirastogi
 * @date 11/15/15
 */
package Room;

import Login.User;

public class Dues
{
	/**
	 * Method to calculate the total bill of a particular booking
	 * @param user User object associated with the booking
	 * @param roomType integer denoting the type of room to get tariff
	 * @param duration integer denoting no. of hours/days room has been booked for
	 * @return
	 */
	public int calculate(User user, int roomType, int duration)
	{
		ConnectRoom cr = new ConnectRoom();
		String sql = "SELECT * FROM `tariff` WHERE `RoomType` = '" + Integer.toString(roomType) + "'";
		int calculated = cr.costCalc(sql, roomType, 0);
		
		int total = calculated * duration * 2 / 5;			//40% advance = * 0.4 = * 2/5
		return total;
	}
	
	/**
	 * Method to update the amount owed by guest
	 * @param user User object whose amount has to be updated
	 * @param bill The cost of current booking
	 */
	void collect(User user,int bill)
	{	
		int dues = 0;
		String add = "SELECT SUM(Amount) FROM `hotel`.`user` WHERE `ID` = '"+user.id+"'";
		
		ConnectRoom cr = new ConnectRoom();
		
		dues = cr.costCalc(add, 0, 1);	//returns present account balance
		System.out.println("Current: " + dues);
		dues = dues + bill;				//new amount
		
		String amt = "UPDATE `hotel`.`user` SET `Amount` = '" + dues + "' WHERE `ID` = '" + user.id + "' AND `user`.`Name` = '" + user.name + "'";
		cr.Connection(amt);
	}
	
	/**
	 * Method to deal with refund on cancellation
	 * @param user User object associated with the booking to be cancelled
	 * @param appno The application no. of booking to be cancelled
	 */
	public void refund(User user, int appno)
	{
		ConnectRoom cr = new ConnectRoom();
		
		String amt = "SELECT * FROM `hotel`.`bookings` WHERE `bookings`.`AppNo` = " + appno;
		int amtr = cr.costCalc(amt, 0, 2);		//bill amount
		
		String add = "SELECT SUM(Amount) FROM `hotel`.`user` WHERE `ID` = '" + user.id + "'";
		int dues = cr.costCalc(add, 0, 1);	//returns present account balance
		
		dues = dues - amtr;
		String latest = "UPDATE `hotel`.`user` SET `Amount` = '" + dues + "' WHERE `ID` = '" + user.id + "' AND `user`.`Name` = '" + user.name + "'";
		cr.Connection(latest);
	}
}
