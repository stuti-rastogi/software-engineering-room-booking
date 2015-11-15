/**
 * Class to send confirmation emails
 * @author stutirastogi
 * @date 11/14/15
 */
package Email;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class SendEmail 
{
	/**
	 * method that sends confirmation e-mail
	 * @param email email address of user to send mail to
	 * @param body body of the message
	 * @param head subject header of the email
	 */
    public static void mail(String email,String body,String head) 
    {
        // Recipient's email ID needs to be mentioned.
        String to = "stuti.r.rastogi@gmail.com";     //change accordingly

        // Sender's email ID needs to be mentioned
        String from = "johnsmith.m0802@gmail.com";       //change accordingly
        
        final String username = "johnsmith.m0802";       //change accordingly
        final String password = "doctorwho0802";       //change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
        new javax.mail.Authenticator() 
        {
            protected PasswordAuthentication getPasswordAuthentication() 
            {
                return new PasswordAuthentication(username, password);
            }
        });

        try 
        {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Set Subject: header field
            message.setSubject(head);

            // Now set the actual message
            message.setText(body);

            // Send message
            Transport.send(message);

            System.out.println("mail Sent successfully....");

        } 
        catch (MessagingException e) 
        {
        	JOptionPane.showMessageDialog(null, "Internet not connected.So, email confirmation is not done.", "Error", JOptionPane.ERROR_MESSAGE);
    			
        }
    }
}