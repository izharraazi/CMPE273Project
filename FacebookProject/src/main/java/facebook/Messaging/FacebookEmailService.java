package facebook.Messaging;  
  
import java.io.UnsupportedEncodingException;  
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;  
import java.util.logging.Level;  
import java.util.logging.Logger;  

import javax.mail.Authenticator;  
import javax.mail.Message;  
import javax.mail.MessagingException;  
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  

import org.springframework.util.StringUtils;
  
public class FacebookEmailService {  
  
    private String SMTP_HOST = "smtp.gmail.com";  
    private String FROM_ADDRESS = "cmpe273.dkhiteam@gmail.com";  
    private String PASSWORD = "cmpe273team";  
    private String FROM_NAME = "Team273";  
    private final String SUBJECT = "Events";
  
    public boolean sendMail(String receiver, String message) {  
        try {  
            Properties props = new Properties();  
            props.put("mail.smtp.host", SMTP_HOST);  
            props.put("mail.smtp.auth", "true");  
            props.put("mail.debug", "false");  
            props.put("mail.smtp.ssl.enable", "true");  
  
            Session session = Session.getInstance(props, new SocialAuth());  
            Message msg = new MimeMessage(session);  
  
            InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);  
            msg.setFrom(from);  
  
            InternetAddress toAddresses = new InternetAddress(receiver);  
            msg.setRecipient(Message.RecipientType.TO, toAddresses);  
            
          //  message = parsemsg(message);
  
            msg.setSubject(SUBJECT);  
            msg.setContent(message, "text/plain");  
            Transport.send(msg);  
            System.out.println("FacebookEmailService.sendMail() Message sent successfully");
            return true;  
        } catch (UnsupportedEncodingException ex) {  
        	System.out.println("MessageFailed");
            return false;  
  
        } catch (MessagingException ex) {  
            return false;  
        }  
    }  
  
    class SocialAuth extends Authenticator {  
  
        @Override  
        protected PasswordAuthentication getPasswordAuthentication() {  
  
            return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);  
  
        }  
    }  
    
    
    private List<String> parseMsg(String msg) {
        List<String> result = new ArrayList();

        if (StringUtils.isEmpty(msg)) {
            return result;
        }

        for (String each : msg.split("~")) {
            result.add(each);
        }

        return result;
    }
  /* public static void main(String[] args) {  
        String recipients = "izharraazi@gmail.com";  
        String subject = "Hi this is test Mail";  
        String messageBody = "Test Mail from team cmpe 273";  
  
        new FacebookEmailService().sendMail(recipients, messageBody);  
  
    }  */
}  