package EmailByJavaImtiazAdar;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Properties;
/*
 * Author : Imtiaz Adar
 * Email : imtiaz-adar@hotmail.com
 * Phone : +8801778-767775, +8801886-187555
 * Program : Sending Email
 * Language : Java
 * Date : 30 / 08 / 2022
 */
public class EmailByImtiazAdar {
    public static void getConnection(String recipient, String subject, String message, String u_email, String u_password, String f_path){
        final String host = "smtp.gmail.com";
        final String port = "587";
        final String protocol = "TLSv1.2";
        try{
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.starttls.enable", true);
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.ssl.protocols", protocol);
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(u_email, u_password);
                }
            });
            Message message1 = sendMessage(session, u_email, recipient, subject, message, f_path);
            Transport.send(message1);
            System.out.println("Email Sent Successfully...");
        }
        catch (Exception e){
            System.out.println("Sending Failed...");
        }
    }

    public static Message sendMessage(Session session, String u_email, String recipient, String subject, String message, String f_path) throws Exception{
        System.out.println("Sending Email !...");
        Message textMessage = new MimeMessage(session);
        textMessage.setFrom(new InternetAddress(u_email));
        textMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        textMessage.setSubject(subject);
        if(!f_path.equals("")) {
            MimeMultipart content = new MimeMultipart();
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Multimessage BY Imtiaz Adar, File Attached Below !");
            MimeBodyPart audioPart = new MimeBodyPart();
            audioPart.attachFile(new File(f_path));
            content.addBodyPart(textPart);
            content.addBodyPart(audioPart);
            textMessage.setContent(content);
        }
        return textMessage;
    }

    public static void main(String[] args) throws Exception{
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader breader = new BufferedReader(reader);
        PrintWriter out = new PrintWriter(System.out);
        out.println("# EMAIL SENDING APPLICATION BY IMTIAZ ADAR #");
        out.flush();
        out.println("# Type YES Or NO To Continue #");
        out.flush();
        String yesOrNo = "";
        while((yesOrNo = breader.readLine()) != null){
            if(yesOrNo.toLowerCase().equals("no")) break;
            out.println("Enter User Email : ");
            out.flush();
            String user_email = breader.readLine();
            out.println("Enter User Password : ");
            out.flush();
            String user_pass = breader.readLine();
            out.println("Enter Recipient");
            out.flush();
            String recipient = breader.readLine();
            out.println("Enter Subject");
            out.flush();
            String subject = breader.readLine();
            out.println("Enter Message : ");
            out.flush();
            String message = breader.readLine();
            out.println("Do You Want To Send Any File? : ");
            out.flush();
            String fileYesOrNo = breader.readLine();
            if(fileYesOrNo.toLowerCase().equals("yes")){
                out.println("Enter File Path : ");
                out.flush();
                String path = breader.readLine();
                getConnection(recipient, subject, message, user_email, user_pass, path);
            }
            else{
                getConnection(recipient, subject, message, user_email, user_pass, "");
            }
            out.println("# Type YES Or NO To Continue #");
            out.flush();
        }
        out.close();
    }
}