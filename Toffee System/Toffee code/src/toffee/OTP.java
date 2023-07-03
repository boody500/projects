/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toffee;

import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;


/**
 *
 * @author Seif
 */
public class OTP {
    public static void sendOTP(String recipientEmail, int otp) {
        // if there is an error like this: 
        // -> ("javax.mail.MessagingException: Could not connect to SMTP host:smtp.gmail.com, port: 587;")
        // So there is a problem with the SMTP server no with the code
        
        // Sender's email credentials
        String senderEmail = "abdelrahmane475@gmail.com";                       //enter a sender email in this empty string
        String senderPassword = "olwqyoefkvefugtj";                             // enter a password of this email from google managment

        // Email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Authenticator to authenticate the sender's email credentials
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        };

        // Create a Session with the properties and authenticator
        Session session = Session.getInstance(properties, authenticator);

        try {
            // Create a MimeMessage
            MimeMessage message = new MimeMessage(session);

            // Set the sender, recipient, subject, and OTP in the message
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Toffee System");
            
            // Create a MimeBodyPart for the text content
            MimeBodyPart textPart = new MimeBodyPart();
            String otpText = "<p style='margin-bottom: 10px;text-align:center;font-size:20px;font-weight:bold;background-color: #eee;'>Hello</p>"
                + "<div style='text-align: center; font-size:25px; padding: 20px;'>"
                + "<strong style='background-color: purple; color: white; padding: 5px;'>" 
                + "Your OTP is: " + otp + "</strong>"
                + "</div>";
            textPart.setContent(otpText, "text/html");


            
            // Create a Multipart object to combine text and HTML parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            // Set the content of the message to the Multipart object
            message.setContent(multipart);
            
            // Send the message
            Transport.send(message);

            System.out.println("OTP sent to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Generate a random otp between 100000 and 999999
    public int generateOTP() {
    Random random = new Random();
    int otp = random.nextInt(900000) + 100000;
    return otp;
    }
}    
