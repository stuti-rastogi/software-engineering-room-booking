package GuiManagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import Room.ConnectRoom;

import Login.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Choice 
{
	JFrame frameChoice;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	

	public Choice(User user) 
	{
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize(final User user) 
	{
		frameChoice = new JFrame();
		frameChoice.setBounds(100, 100, 450, 300);
		frameChoice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameChoice.getContentPane().setLayout(null);
		
		JLabel lblWelcomeUser = new JLabel("Welcome "+user.name);
		lblWelcomeUser.setBounds(39, 11, 200, 30);
		frameChoice.getContentPane().add(lblWelcomeUser);
		
		JLabel lblWhatWouldYou = new JLabel("What would you like to do?");
		lblWhatWouldYou.setBounds(99, 52, 244, 30);
		frameChoice.getContentPane().add(lblWhatWouldYou);
		
		JButton btnRoomBooking = new JButton("Room Booking");
		btnRoomBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{ 
							frameChoice.setVisible(false);
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

		btnRoomBooking.setBounds(109, 147, 146, 30);
		frameChoice.getContentPane().add(btnRoomBooking);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JOptionPane.showMessageDialog (null, "You are logged out.", "logout", JOptionPane.INFORMATION_MESSAGE);
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{
							frameChoice.setVisible(false);
							Login window = new Login();
							window.frameLogin.setVisible(true);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		
		btnLogout.setBounds(109, 206, 146, 30);
		frameChoice.getContentPane().add(btnLogout);
		
		JLabel lblYourDues = new JLabel("Your current dues - ");
		lblYourDues.setBounds(223, 19, 140, 14);
		frameChoice.getContentPane().add(lblYourDues);
		
		String query1 = "SELECT * FROM `user` WHERE `Name` = '"+user.name+"'";
		ConnectRoom cr = new ConnectRoom();
		int dues = cr.getdues(query1);
		//System.out.println("Dues query: " + query1);
		System.out.println(dues);
		
		JLabel label = new JLabel(Integer.toString(dues));
		label.setBounds(378, 19, 46, 14);
		frameChoice.getContentPane().add(label);
	}

}
