/**
 * Class that shows the first screen when admin logs in
 * @author stutirastogi
 * @date 11/14/15
 */
package GuiManagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Login.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Admin 
{
	JFrame frameAdmin;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public Admin(User user) 
	{
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final User user) 
	{
		//main frame
		frameAdmin = new JFrame();
		frameAdmin.setBounds(100, 100, 450, 300);
		frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameAdmin.getContentPane().setLayout(null);
		
		//welcome message
		JLabel lblWelcomeAdmin = new JLabel("Welcome "+user.name);
		lblWelcomeAdmin.setBounds(24, 23, 125, 34);
		frameAdmin.getContentPane().add(lblWelcomeAdmin);
		
		JLabel lblWhatDoYou = new JLabel("Click below to handle bookings");
		lblWhatDoYou.setBounds(153, 49, 210, 34);
		frameAdmin.getContentPane().add(lblWhatDoYou);
		
		JButton btnRoom = new JButton("Rooms");
		btnRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{ 
							frameAdmin.setVisible(false);
							Adminroom window = new Adminroom(user);			//call room approval for admin
							window.frameAdminroom.setVisible(true);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnRoom.setBounds(163, 158, 89, 23);
		frameAdmin.getContentPane().add(btnRoom);
		
		//logout button
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JOptionPane.showMessageDialog (null, "You are logged out", "logout", JOptionPane.INFORMATION_MESSAGE);
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{
							frameAdmin.setVisible(false);
							Login window = new Login();					//send back to login screen after logout
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
		btnLogout.setBounds(163, 205, 89, 23);
		frameAdmin.getContentPane().add(btnLogout);
	}

}
