/**
 * Class that handles room approval screen for admin
 * @author stutirastogi
 * @date 11/14/15
 */
package GuiManagement;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Login.User;
import Room.ConnectRoom;
import Room.RoomResource;
import Room.RoomDB;

import javax.swing.JTextField;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

public class Adminroom 
{
	JFrame frameAdminroom;
	private JTextField textField;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public Adminroom(User user) 
	{
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final User user) 
	{
		//new main frame
		frameAdminroom = new JFrame();
		frameAdminroom.setBounds(100, 100, 450, 300);
		frameAdminroom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameAdminroom.getContentPane().setLayout(null);
		
		JButton btnReturnToPrevious = new JButton("Return to previous");
		btnReturnToPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{	 
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{ 
							frameAdminroom.setVisible(false);
							Admin window = new Admin(user);			//show main admin screen which was previous menu
							window.frameAdmin.setVisible(true);
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
		frameAdminroom.getContentPane().add(btnReturnToPrevious);

		//check if there are any ungranted requests
		ConnectRoom crc = new ConnectRoom();
		String details = "SELECT * FROM `bookings` WHERE `Granted` = '0'";
		String junk = crc.Search(details, 2, null);
		
		if(junk.matches("0"))
		{
			//no queued requests
			JLabel lblYourEarlierBookings = new JLabel("You do not have any applications pending .");
			lblYourEarlierBookings.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblYourEarlierBookings.setBounds(58, 75, 302, 62);
			frameAdminroom.getContentPane().add(lblYourEarlierBookings);
		}
		
		else
		{
			//drop down menu for the requests
			final JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setBounds(223, 70, 186, 44);
			frameAdminroom.getContentPane().add(lblNewLabel);
				
			final JComboBox comboBox_1 = new JComboBox();
			comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Accept", "Deny"}));
			comboBox_1.setBounds(223, 133, 87, 20);
			frameAdminroom.getContentPane().add(comboBox_1);
			
			//not needed always - can remove (discuss with designers)
			JLabel lblReasonGivenBy = new JLabel("Reason given by applicant");
			lblReasonGivenBy.setBounds(20, 79, 195, 14);
			frameAdminroom.getContentPane().add(lblReasonGivenBy);
				
			JLabel lblReasonForDenying = new JLabel("Reason for denying");
			lblReasonForDenying.setBounds(20, 173, 153, 14);
			frameAdminroom.getContentPane().add(lblReasonForDenying);
				
			textField = new JTextField();
			textField.setBounds(224, 164, 200, 39);
			frameAdminroom.getContentPane().add(textField);
			textField.setColumns(10);
			textField.setText(" ");
				
			JLabel lblOpenTheDrop = new JLabel("Open the drop down menu to see the applications");
			lblOpenTheDrop.setBounds(31, 29, 393, 14);
			frameAdminroom.getContentPane().add(lblOpenTheDrop);
				
			ConnectRoom cr = new ConnectRoom();
			String[] entries = cr.getOldEntries(details);
			
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
			comboBox.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuCanceled(PopupMenuEvent arg0) 
				{

				}
				
				public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) 
				{
					String inp = comboBox.getSelectedItem().toString();		//string from drop down menu
					String inpid = inp.substring(16, 18);			//application no.
					String sql = "SELECT * FROM `hotel`.`bookings` WHERE `bookings`.`AppNo` = "+inpid;
					ConnectRoom cr = new ConnectRoom();
					lblNewLabel.setText(cr.getreason(sql));					
				}
				
				public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) 
				{
				
				}
			});
			
			comboBox.setModel(new DefaultComboBoxModel(finale));
			comboBox.setMaximumRowCount(40);
			comboBox.setBounds(31, 54, 372, 20);
			frameAdminroom.getContentPane().add(comboBox);
				
			JButton btnConfirm = new JButton("Confirm");
			btnConfirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) 
				{
					int ans=0;
					ConnectRoom cr = new ConnectRoom();
					String order = comboBox_1.getSelectedItem().toString();
					
					String inp = comboBox.getSelectedItem().toString();
					String inpid = inp.substring(16, 18);			//application no.
					String rno = inp.substring(25, 29);				//room no.
					if(order.matches("Accept"))
					{
						//admin approved
						ans = 1;
						
						//change granted of booking
						String ordersql = "UPDATE `hotel`.`bookings` SET `Granted` = '"+ans+"', `ReasonDenied` = '"+textField.getText()+"' WHERE `bookings`.`AppNo` = '"+inpid+"';";
						cr.Connection(ordersql);
						
						//change the booked status of room
						String order2 = "UPDATE `hotel`.`rooms` SET `isBooked` = '1' WHERE `rooms`.`RoomNo` = '"+rno+"';";
						cr.Connection(order2);	
					}
					else
					{
						ans = 0;
						//granted should be 0
						String ordersql = "UPDATE `hotel`.`bookings` SET `Granted` = '"+ans+"', `Reason denied` = '"+textField.getText()+"' WHERE `room`.`AppNo` = '"+inpid+"';";
						
						//execute query
						cr.Connection(ordersql);
					}
						
					RoomResource ro;
					try 
					{
						//to send email
						ro = new RoomResource();
						ro.AppNo = Integer.parseInt(inpid);
						ro.reasondenied = textField.getText();
						
						String query2 = "SELECT * FROM `bookings` WHERE `AppNo` = "+ro.AppNo;
						cr.Search(query2, 3, ro);
					} 
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
						
					JOptionPane.showMessageDialog (null, "Application No. "+inpid+" has been successfully updated", "Cancellation", JOptionPane.INFORMATION_MESSAGE);
					EventQueue.invokeLater(new Runnable() {
						public void run() 
						{
							try 
							{
								frameAdminroom.setVisible(false);
								Admin window = new Admin(user);				//previous screen
								window.frameAdmin.setVisible(true);
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							}
						}
					});
				}
			});
			
			btnConfirm.setBounds(260, 214, 89, 23);
			frameAdminroom.getContentPane().add(btnConfirm);
		}
	}
}
