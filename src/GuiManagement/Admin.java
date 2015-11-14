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
		frameAdmin = new JFrame();
		frameAdmin.setBounds(100, 100, 450, 300);
		frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameAdmin.getContentPane().setLayout(null);
		
		JLabel lblWelcomeAdmin = new JLabel("Welcome "+user.name);
		lblWelcomeAdmin.setBounds(24, 23, 105, 34);
		frameAdmin.getContentPane().add(lblWelcomeAdmin);
		
		JLabel lblWhatDoYou = new JLabel("What do you want to do ?");
		lblWhatDoYou.setBounds(153, 49, 198, 34);
		frameAdmin.getContentPane().add(lblWhatDoYou);
		
		JButton btnRoom = new JButton("Room");
		btnRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{ 
							frameAdmin.setVisible(false);
							Adminroom window = new Adminroom(user);
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
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JOptionPane.showMessageDialog (null, "Logging out...", "logout", JOptionPane.INFORMATION_MESSAGE);
				EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
						try 
						{
							frameAdmin.setVisible(false);
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
		btnLogout.setBounds(163, 205, 89, 23);
		frameAdmin.getContentPane().add(btnLogout);
	}

}
