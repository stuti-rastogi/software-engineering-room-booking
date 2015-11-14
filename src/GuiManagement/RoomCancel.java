package GuiManagement;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Login.User;
import Room.ConnectRoom;
import Room.RoomDB;

public class RoomCancel 
{

	JFrame frameRoomCancel;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public RoomCancel(User user) throws SQLException 
	{
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize(final User user) throws SQLException {
		frameRoomCancel = new JFrame();
		frameRoomCancel.setBounds(100, 100, 450, 300);
		frameRoomCancel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameRoomCancel.getContentPane().setLayout(null);
		
		JButton btnReturnToPrevious = new JButton("Return to previous page");
		btnReturnToPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				frameRoomCancel.setVisible(false);
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{	
							RoomChoice window1 = new RoomChoice(user);
							window1.frameRoomChoice.setVisible(true);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		btnReturnToPrevious.setBounds(191, 212, 206, 39);
		frameRoomCancel.getContentPane().add(btnReturnToPrevious);
		
		try
		{
			RoomDB rodb = new RoomDB();
			String junk = rodb.checkDB(user);
			
			if(junk.matches("0"))
			{
				JLabel lblYourEarlierBookings = new JLabel("You do not have any bookings.");
				lblYourEarlierBookings.setFont(new Font("Tahoma", Font.BOLD, 18));
				lblYourEarlierBookings.setBounds(58, 75, 302, 62);
				frameRoomCancel.getContentPane().add(lblYourEarlierBookings);
			}
			else
			{
				JLabel lblYourEarlierBookings = new JLabel("Your earlier bookings are :-");
				lblYourEarlierBookings.setBounds(10, 0, 152, 26);
				frameRoomCancel.getContentPane().add(lblYourEarlierBookings);
				
				ConnectRoom cr = new ConnectRoom();
				String sql = "SELECT * FROM `hotel`.`bookings` WHERE `GuestName` = '"+user.name+"'";
				String[] entries = cr.getOldEntries(sql);
				int total = 0;
				
				for(int i = 0; i < entries.length; i++)
				{
					if(entries[i] == null)
					{
						break;
					}
					else
					{
						total++;
					}
				}

				String[] finale = new String[total];
				
				for(int i = 0; i < total; i++)
				{
					finale[i]=entries[i];
				}
				
				final JComboBox comboBox = new JComboBox();
				comboBox.setModel(new DefaultComboBoxModel(finale));
				comboBox.setBounds(10, 22, 414, 20);
				frameRoomCancel.getContentPane().add(comboBox);
				
				JButton btnCancelBooking = new JButton("Cancel booking");
				btnCancelBooking.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) 
					{
						ConnectRoom cr1 = new ConnectRoom();
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
						String inp = comboBox.getSelectedItem().toString();
						String inpid = inp.substring(16, 18);
						String roomno = inp.substring(25, 29);
						int appid = Integer.parseInt(inpid);
						
						String query = "SELECT * FROM `hotel`.`bookings` WHERE `bookings`.`AppNo` = "+appid;
						String bookdate = cr1.getDate(query);
						System.out.println(bookdate);
						
						try 
						{
							Date dobook = sdf.parse(bookdate);
							Calendar cal = Calendar.getInstance();
							Calendar calnew = Calendar.getInstance();
							cal.setTime(new Date());
							calnew.setTime(dobook);				// sets calendar time/date
							cal.add(Calendar.HOUR_OF_DAY, 1); 	// adds one hour
							if(calnew.compareTo(cal) < 0)
							{ 
								//checks whether cal>calnew
								JOptionPane.showMessageDialog(null, "You cannot cancel your room booking now.", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else
							{
								try 
								{
									RoomDB db2 = new RoomDB();
									db2.cancelDB(appid, roomno, user);
									JOptionPane.showMessageDialog (null, "Room has been successfully cancelled.", "Cancellation", JOptionPane.INFORMATION_MESSAGE);
									EventQueue.invokeLater(new Runnable() {
										public void run() 
										{
											try 
											{
												frameRoomCancel.setVisible(false);
												RoomChoice window1 = new RoomChoice(user);
												window1.frameRoomChoice.setVisible(true);
											} 
											catch (Exception e) 
											{
												e.printStackTrace();
											}
										}
									});
								} 
								catch (SQLException e) 
								{
									e.printStackTrace();
								}
							}	
						} 
						catch (ParseException e1) 
						{
							e1.printStackTrace();
						}
					}
				});
				
				btnCancelBooking.setBounds(191, 162, 206, 39);
				frameRoomCancel.getContentPane().add(btnCancelBooking);
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
