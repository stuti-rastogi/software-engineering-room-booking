package GuiManagement;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
		
		JLabel lblRoomNo = new JLabel("Room No.");
		lblRoomNo.setBounds(52, 36, 69, 14);
		frameBookroom.getContentPane().add(lblRoomNo);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"c301", "c302", "c303", "c304", "c305", "c306", "c307", "c308", "a501", "a502", "a503", "a504", "a505", "a506", "a507", "a508"}));
		comboBox.setBounds(227, 33, 60, 20);
		frameBookroom.getContentPane().add(comboBox);
		
		JLabel lblSelectDateOf = new JLabel("Select Date and time of booking");
		lblSelectDateOf.setBounds(10, 61, 219, 20);
		frameBookroom.getContentPane().add(lblSelectDateOf);
		
		final JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerDateModel(new Date(1416421800000L), new Date(1416421800000L), new Date(1420050540000L), Calendar.DAY_OF_MONTH));
		spinner.setBounds(227, 61, 148, 20);
		frameBookroom.getContentPane().add(spinner);
		
		JLabel lblEnterTheDuration = new JLabel("Enter the duration (hours)");
		lblEnterTheDuration.setBounds(20, 92, 163, 14);
		frameBookroom.getContentPane().add(lblEnterTheDuration);
		
		final JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinner_1.setBounds(227, 92, 44, 20);
		frameBookroom.getContentPane().add(spinner_1);
		
		JButton btnNewButton = new JButton("Check Availability");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try 
				{
					RoomResource ro = new RoomResource();
					ro.roomno = (String) comboBox.getSelectedItem();
					ro.duration = Integer.parseInt(spinner_1.getValue().toString()); //hours
					
					Calendar cal = Calendar.getInstance();
					Calendar calnew = Calendar.getInstance();
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_MONTH, 1);
			        
			        String date = spinner.getValue().toString();
			        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
			        Date dobook = sdf.parse(date);
			        calnew.setTime(dobook);
					
					if(calnew.compareTo(cal) < 0)
					{ 
						//checks whether cal>calnew
						JOptionPane.showMessageDialog(null, "You  have book room atleast 1 day earlier....", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					else
					{
						ro.day = dobook.getDate();
						ro.mon = dobook.getMonth()+1;
						ro.year = 2014;
						System.out.println(ro.year);
						
						ro.hr = dobook.getHours();
						ro.min = dobook.getMinutes();
						ro.start = ro.hr+(ro.min/100);
						ro.end = ro.start+ro.duration;
						
						RoomDB rdb = new RoomDB();
						boolean avail = rdb.RoomChk(ro);
						
						if(avail==false)
						{
							JOptionPane.showMessageDialog(null, "Selected room is not available...Select another room", "Error", JOptionPane.ERROR_MESSAGE);	
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
		
		JLabel lblReason = new JLabel("Reason");
		lblReason.setBounds(52, 160, 46, 14);
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
					ro.roomno = (String) comboBox.getSelectedItem();
					ro.duration = Integer.parseInt(spinner_1.getValue().toString()); //hours
					
					Calendar cal = Calendar.getInstance();
					Calendar calnew = Calendar.getInstance();
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_MONTH, 1);
			        
			        String date = spinner.getValue().toString();
			        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
			        Date dobook = sdf.parse(date);
			        calnew.setTime(dobook);
					if(calnew.compareTo(cal) < 0)
					{ 
						//checks whether cal>calnew
						JOptionPane.showMessageDialog(null, "You  have book room atleast 1 day earlier....", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						ro.day = dobook.getDate();
						ro.mon = dobook.getMonth()+1;
						ro.year = 2014;
						
						System.out.println(ro.year);
						
						ro.hr = dobook.getHours();
						ro.min = dobook.getMinutes();
						ro.start = ro.hr+(ro.min/100);//System.out.println(ro.start);
						ro.end = ro.start+ro.duration;//System.out.println(ro.end);
						
						RoomDB rdb = new RoomDB();
						boolean avail = rdb.RoomChk(ro);
						if(avail == false)
						{
							JOptionPane.showMessageDialog(null, "Selected room is not available...Select another room", "Error", JOptionPane.ERROR_MESSAGE);
							
						}
						else
						{
							ro.reason = formattedTextField.getText();
							JOptionPane.showMessageDialog (null,"Selected room is available for mentioned duration", "Available", JOptionPane.INFORMATION_MESSAGE);
							int x =JOptionPane.showConfirmDialog(null, "Do you want to book room", "Confirm booking",JOptionPane.YES_NO_OPTION);
							
							if(x == JOptionPane.YES_OPTION)
							{
								rdb.queue(ro, user);
								JOptionPane.showMessageDialog (null,"Room successfully queued", "Successful", JOptionPane.INFORMATION_MESSAGE);
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
