package GuiManagement;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;
import java.util.concurrent.*;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JTextField;

import Login.User;
import Room.Dues;
import Room.RoomResource;
import Room.RoomDB;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BookRoom 
{

	JFrame frameBookroom;
	private JTextField textField;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public BookRoom(User user) 
	{
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final User user) 
	{
		frameBookroom = new JFrame();
		frameBookroom.setBounds(100, 100, 450, 300);
		frameBookroom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameBookroom.getContentPane().setLayout(null);
		
		JLabel lblEnterTheDetails = new JLabel("Enter the details of the room");
		lblEnterTheDetails.setBounds(30, 11, 241, 14);
		frameBookroom.getContentPane().add(lblEnterTheDetails);
		
		JLabel lblRoomNo = new JLabel("Room Type");
		lblRoomNo.setBounds(52, 36, 75, 14);
		frameBookroom.getContentPane().add(lblRoomNo);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Deluxe", "Suite", "Conference Room", "Banquet Hall"}));
		comboBox.setBounds(227, 33, 100, 20);
		frameBookroom.getContentPane().add(comboBox);
		
		JLabel lblSelectDateOf = new JLabel("Select Start Date and time");
		lblSelectDateOf.setBounds(10, 61, 219, 20);
		frameBookroom.getContentPane().add(lblSelectDateOf);
		
		final JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerDateModel(new Date(1448080200000L), new Date(1448080200000L), new Date(1479702600000L), Calendar.DAY_OF_MONTH));
		spinner.setBounds(227, 61, 155, 20);
		frameBookroom.getContentPane().add(spinner);
		
		JLabel lblEndDateOf = new JLabel("Select End Date and time");
		lblEndDateOf.setBounds(10, 101, 219, 20);
		frameBookroom.getContentPane().add(lblEndDateOf);
		
		final JSpinner spinnerEnd = new JSpinner();
		spinnerEnd.setModel(new SpinnerDateModel(new Date(1448080200000L), new Date(1448080200000L), new Date(1479702600000L), Calendar.DAY_OF_MONTH));
		spinnerEnd.setBounds(227, 101, 148, 20);
		frameBookroom.getContentPane().add(spinnerEnd);
		
		JButton btnNewButton = new JButton("Check Availability");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try 
				{
					RoomResource ro = new RoomResource();
					String roomType = (String) comboBox.getSelectedItem();
					
					if (roomType.matches("Deluxe"))
						ro.roomtype = 1;
					else if (roomType.matches("Suite"))
						ro.roomtype = 2;
					else if (roomType.matches("Conference Room"))
						ro.roomtype = 3;
					else if (roomType.matches("Banquet Hall"))
						ro.roomtype = 4;
					
					Calendar cal = Calendar.getInstance();
					Calendar calnew = Calendar.getInstance();
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_MONTH, 1);
			        
			        String date = spinner.getValue().toString();
			        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
			        Date dobook = sdf.parse(date);
			        calnew.setTime(dobook);
			        
			        Timestamp startTime = new Timestamp(dobook.getTime());
			        
			        String endDate = spinnerEnd.getValue().toString();
			        SimpleDateFormat sdfEnd = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
			        Date dobookEnd = sdf.parse(endDate);
					
			        Timestamp endTime = new Timestamp(dobookEnd.getTime());
			        
					if(calnew.compareTo(cal) < 0)
					{ 
						//checks whether cal>calnew
						JOptionPane.showMessageDialog(null, "You  have book room atleast 1 day earlier....", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					else
					{
						ro.startTime = startTime;
						ro.endTime = endTime;
						
						RoomDB rdb = new RoomDB();
						String avail = rdb.roomCheck(ro);
						
						if(avail.matches("problem"))
						{
							JOptionPane.showMessageDialog(null, "Sorry, selected room is not available.", "Error", JOptionPane.ERROR_MESSAGE);	
						}
						else
						{
							JOptionPane.showMessageDialog (null,"Selected room is available for mentioned duration", "Available", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				} 
				catch (ParseException e) 
				{
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(227, 123, 160, 23);
		frameBookroom.getContentPane().add(btnNewButton);
		
		JLabel lblReason = new JLabel("Reason (for conference room/banquet hall bookings only)");
		lblReason.setBounds(52, 160, 146, 14);
		frameBookroom.getContentPane().add(lblReason);
		
		final JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setText("");
		formattedTextField.setBounds(227, 157, 182, 39);
		frameBookroom.getContentPane().add(formattedTextField);
		
		JButton btnSubmitApplication = new JButton("Submit Application");
		btnSubmitApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try 
				{
					RoomResource ro = new RoomResource();
					String roomType = (String) comboBox.getSelectedItem();
					
					if (roomType.matches("Deluxe"))
						ro.roomtype = 1;
					else if (roomType.matches("Suite"))
						ro.roomtype = 2;
					else if (roomType.matches("Conference Room"))
						ro.roomtype = 3;
					else if (roomType.matches("Banquet Hall"))
						ro.roomtype = 4;
					
					Calendar cal = Calendar.getInstance();
					Calendar calnew = Calendar.getInstance();
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_MONTH, 1);
			        
					String date = spinner.getValue().toString();
			        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
			        Date dobook = sdf.parse(date);
			        calnew.setTime(dobook);
			        
			        Timestamp startTime = new Timestamp(dobook.getTime());
			        
			        String endDate = spinnerEnd.getValue().toString();
			        SimpleDateFormat sdfEnd = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
			        Date dobookEnd = sdf.parse(endDate);
					
			        Timestamp endTime = new Timestamp(dobookEnd.getTime());
			        
					if(calnew.compareTo(cal) < 0)
					{ 
						//checks whether cal>calnew
						JOptionPane.showMessageDialog(null, "You  have book room atleast 1 day earlier....", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						ro.startTime = startTime;
						ro.endTime = endTime;
						Long diff = ro.endTime.getTime() - ro.startTime.getTime();
						if (ro.roomtype > 2)
						{
							//per hour basis
							ro.duration = (int)TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
						}
						else
						{
							//per day basis
							ro.duration = (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
						}
						
						RoomDB rdb = new RoomDB();
						String avail = rdb.roomCheck(ro);
						
						if(avail.matches("problem"))
						{
							JOptionPane.showMessageDialog(null, "Sorry, selected room is not available.", "Error", JOptionPane.ERROR_MESSAGE);	
						}
						else
						{
							ro.roomno = avail;
							ro.reason = formattedTextField.getText();
							JOptionPane.showMessageDialog (null,"Selected room is available for mentioned duration", "Available", JOptionPane.INFORMATION_MESSAGE);
							int x =JOptionPane.showConfirmDialog(null, "Do you want to book room", "Confirm booking",JOptionPane.YES_NO_OPTION);
							
							if(x == JOptionPane.YES_OPTION)
							{
								rdb.queue(ro, user);
								JOptionPane.showMessageDialog (null,"Application received", "Successful", JOptionPane.INFORMATION_MESSAGE);
								EventQueue.invokeLater(new Runnable() {
									public void run() 
									{
										try 
										{ 
											frameBookroom.setVisible(false);
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
							else
							{

							}
							
						}
					}
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				} 
				catch (ParseException e) 
				{
					e.printStackTrace();
				}
			}
		});
		
		btnSubmitApplication.setBounds(227, 207, 160, 23);
		frameBookroom.getContentPane().add(btnSubmitApplication);
		
		JButton btnReturnToPrevious = new JButton("Return to previous menu");
		btnReturnToPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{ 
							frameBookroom.setVisible(false);
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
		btnReturnToPrevious.setBounds(10, 207, 207, 23);
		frameBookroom.getContentPane().add(btnReturnToPrevious);
	}
}
