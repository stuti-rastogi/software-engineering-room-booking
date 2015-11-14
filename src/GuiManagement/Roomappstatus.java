package GuiManagement;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

import Login.User;
import Room.ConnectRoom;
import Room.RoomDB;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;

public class Roomappstatus 
{

	JFrame frameRoomappstatus;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public Roomappstatus(User user) 
	{
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final User user) 
	{
		frameRoomappstatus = new JFrame();
		frameRoomappstatus.setBounds(100, 100, 450, 300);
		frameRoomappstatus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameRoomappstatus.getContentPane().setLayout(null);
		
		JButton btnReturnToPrevious = new JButton("Return to previous");
		btnReturnToPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{	 
				EventQueue.invokeLater(new Runnable() {
				public void run() 
				{
					try 
					{ 
						frameRoomappstatus.setVisible(false);
						RoomChoice window = new RoomChoice(user);
						window.frameRoomChoice.setVisible(true);
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				});	
			}
		});
		
		
		btnReturnToPrevious.setBounds(63, 214, 167, 23);
		frameRoomappstatus.getContentPane().add(btnReturnToPrevious);
		
		try
		{ 
			RoomDB db1 = new RoomDB();
			String junk = db1.checkDB(user);
			if(junk.matches("0"))
			{
				JLabel lblYourEarlierBookings = new JLabel("You do not have any bookings.");
				lblYourEarlierBookings.setFont(new Font("Tahoma", Font.BOLD, 18));
				lblYourEarlierBookings.setBounds(58, 75, 302, 62);
				frameRoomappstatus.getContentPane().add(lblYourEarlierBookings);
			}
			
			else
			{
				JLabel lblOpenTheDrop = new JLabel("Open the drop down menu to see the status of your application");
				lblOpenTheDrop.setBounds(31, 29, 393, 14);
				frameRoomappstatus.getContentPane().add(lblOpenTheDrop);
				
				ConnectRoom cr = new ConnectRoom();
				String sql = "SELECT * FROM `oop`.`room` WHERE `Student` = '"+user.name+"'";
				String[] entries = cr.getOldEntries(sql);
				int total=0;
				
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

				JComboBox comboBox = new JComboBox();
				comboBox.setModel(new DefaultComboBoxModel(finale));
				comboBox.setMaximumRowCount(40);
				comboBox.setBounds(31, 54, 372, 20);
				frameRoomappstatus.getContentPane().add(comboBox);
			}
			
			
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
