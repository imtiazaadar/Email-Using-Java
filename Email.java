package email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.internet.MimeMultipart;

/**
 * Imtiaz Adar
 * Email
 */
public class Email {
    public static void setupConnection(String email, String password, String recipient, String subject, String text, String path){
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.starttls.enable", true);
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", 587);
            properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });
            Message message = sendMail(session, email, recipient, subject, text, path);
            Transport.send(message);
            System.out.println("Sent Successfully !!");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Message sendMail(Session session, String email, String recipient, String subject, String text, String path){
        try{
            System.out.println("Sending Email...");
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(text);
            if(!path.equals("")) {
                MimeMultipart content = new MimeMultipart();
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText("Multi Message");
                MimeBodyPart contPart = new MimeBodyPart();
                File file = new File(path);
                contPart.attachFile(file);
                content.addBodyPart(textPart);
                content.addBodyPart(contPart);
                message.setContent(content);
            }
            return message;
        }
        catch(Exception e){
            System.out.println("Failed !!");
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("User Email : ");
        String email = scan.nextLine();
        System.out.println("User Password : ");
        String password = scan.nextLine();
        System.out.println("Recipient's Email : ");
        String recipient = scan.nextLine();
        System.out.println("Subject : ");
        String subject = scan.nextLine();
        System.out.println("Message : ");
        String text = scan.nextLine();
        System.out.println("Want To Add Attachment ?");
        String stat = scan.nextLine();
        if(stat.toLowerCase().equals("yes")){
            System.out.println("File Path : ");
            String path = scan.nextLine();
            setupConnection(email, password, recipient, subject, text, path);
        }
        else
            setupConnection(email, password, recipient, subject, text, "");
    }
}
