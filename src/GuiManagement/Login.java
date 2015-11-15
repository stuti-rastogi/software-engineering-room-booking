/**
 * Class for login screen
 * @author stutirastogi
 * @date 11/14/15
 */
package GuiManagement;
import Login.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.JRadioButton;

public class Login 
{
	JFrame frameLogin;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try 
				{
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

	/**
	 * Create the application.
	 */
	public Login() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frameLogin = new JFrame();
		frameLogin.setBounds(100, 100, 450, 300);
		frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLogin.getContentPane().setLayout(null);
		
		JLabel lblUserName = new JLabel("User Name ");
		lblUserName.setBounds(74, 88, 90, 23);
		frameLogin.getContentPane().add(lblUserName);
		
		//for username
		textField = new JTextField();
		textField.setBounds(163, 89, 86, 20);
		frameLogin.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(74, 138, 65, 14);
		frameLogin.getContentPane().add(lblPassword);
		
		//for password
		passwordField = new JPasswordField();
		passwordField.setBounds(163, 135, 86, 20);
		frameLogin.getContentPane().add(passwordField);
		
		JLabel lblWelcomeToOur = new JLabel("Welcome to Silver Sands Hotel");
		lblWelcomeToOur.setFont(new Font("Sylfaen", Font.BOLD, 18));
		lblWelcomeToOur.setBounds(109, 23, 324, 54);
		frameLogin.getContentPane().add(lblWelcomeToOur);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				String username = textField.getText();			
				String passwd = passwordField.getText();			//TODO: Check new method, this works
				System.out.println(passwd);
				UserDB udb = new UserDB();
				try 
				{
					String ans = udb.UserDBVerify(username, passwd);		//get login verification
					String adm = ans.substring(0,1);	//first character gives admin or not
					System.out.println(ans);
					if (ans.matches("0Successful")|| ans.matches("1Successful"))
					{
						if(adm.matches("1"))
						{
							//this is admin
							final User user = udb.isVerified();			//new user object based on credentials
							EventQueue.invokeLater(new Runnable() {
								public void run() 
								{
									try 
									{
										frameLogin.setVisible(false);
										Admin window = new Admin(user);			//admin operations screen
										window.frameAdmin.setVisible(true);
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
							frameLogin.setVisible(false);
							final User user = udb.isVerified();

							EventQueue.invokeLater(new Runnable() {
								public void run() 
								{
									try 
									{
										Choice window = new Choice(user);		//user operations screen
										window.frameChoice.setVisible(true);
										
									} 
									catch (Exception e) 
									{
										System.out.println(user.name);
										e.printStackTrace();
									}
								}
							});
						}
						
						//edit this
					}
					
					else 
					{
						JOptionPane.showMessageDialog(null, "Invalid details", "Error", JOptionPane.ERROR_MESSAGE);
						
					}		
				} 
				catch (Exception e) 
				{
					JOptionPane.showMessageDialog(null, "Database not connected.", "Error", JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		
		btnLogin.setBounds(163, 176, 89, 23);
		frameLogin.getContentPane().add(btnLogin);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JOptionPane.showMessageDialog (null, "Thank You for visiting us", "Exiting", JOptionPane.INFORMATION_MESSAGE);
				frameLogin.setVisible(false);
				System.exit(0);
			}
		});
		
		btnExit.setBounds(160, 216, 89, 23);
		frameLogin.getContentPane().add(btnExit);
	}
}
