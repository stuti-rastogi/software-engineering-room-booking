package GuiManagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

import Login.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RoomChoice 
{
	JFrame frameRoomChoice;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public RoomChoice(User user) 
	{
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final User user) 
	{
		frameRoomChoice = new JFrame();
		frameRoomChoice.setBounds(100, 100, 450, 300);
		frameRoomChoice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameRoomChoice.getContentPane().setLayout(null);
		
		JLabel lblMakeAChoice = new JLabel("Make a choice");
		lblMakeAChoice.setBounds(33, 11, 140, 14);
		frameRoomChoice.getContentPane().add(lblMakeAChoice);
		
		JButton btnMakeNewBooking = new JButton("Make new booking");
		btnMakeNewBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{
							frameRoomChoice.setVisible(false);
							BookRoom window = new BookRoom(user);
							window.frameBookroom.setVisible(true);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		btnMakeNewBooking.setBounds(145, 55, 224, 23);
		frameRoomChoice.getContentPane().add(btnMakeNewBooking);
		
		JButton btnCheckStatusOf = new JButton("Check status of earlier booking");
		btnCheckStatusOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{
							frameRoomChoice.setVisible(false);
							Roomappstatus window = new Roomappstatus(user);
							window.frameRoomappstatus.setVisible(true);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		btnCheckStatusOf.setBounds(145, 99, 224, 23);
		frameRoomChoice.getContentPane().add(btnCheckStatusOf);
		
		JButton btnCancelBooking = new JButton("Cancel Booking");
		btnCancelBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{
							frameRoomChoice.setVisible(false);
							RoomCancel window = new RoomCancel(user);
							window.frameRoomCancel.setVisible(true);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnCancelBooking.setBounds(145, 148, 224, 23);
		frameRoomChoice.getContentPane().add(btnCancelBooking);
		
		JButton btnNewButton = new JButton("Return to previous menu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{	
							frameRoomChoice.setVisible(false);
							Choice window = new Choice(user);
							window.frameChoice.setVisible(true);
							
						} 
						catch (Exception e) 
						{
							//System.out.println(user.name);
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		btnNewButton.setBounds(145, 193, 224, 23);
		frameRoomChoice.getContentPane().add(btnNewButton);
	}

}
