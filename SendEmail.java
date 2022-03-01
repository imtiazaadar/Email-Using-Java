package EmailProject;

import java.io.File;
import java.util.Properties;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.PasswordAuthentication;
/*
 * Author : Imtiaz Adar
 */
public class SendEmail {
	public static void getConnection(String recipient, String subject, String message, String email, String password) {
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", true);
			properties.put("mail.smtp.starttls.enable", true);
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", 587);
			properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
			Session session = Session.getInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email, password);
				}
			});
			Message mess = sendMessage(email, recipient, subject, message, session);
			Transport.send(mess);
			System.out.println("Sent !");
		}
		catch(Exception e) {
			System.out.println("Failed !");
		}
	}
	public static Message sendMessage(String email, String recipient, String subject, String message, Session session) throws Exception {
		System.out.println("Sending Email...");
		Message text = new MimeMessage(session);
		text.setFrom(new InternetAddress(email));
		text.addRecipient(RecipientType.TO, new InternetAddress(recipient));
		text.setSubject(subject);
		text.setText(message);
		MimeMultipart content = new MimeMultipart();
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setText("Message from Imtiaz Adar with attached files...");
		MimeBodyPart pdfPart = new MimeBodyPart();
		pdfPart.attachFile(new File("C:\\Users\\imtia\\OneDrive\\Documents\\Eclipse Program\\EmailProgram\\src\\EmailProject\\mypdf.pdf"));
		content.addBodyPart(textPart);
		content.addBodyPart(pdfPart);
		text.setContent(content);
		return text;
	}
	public static void main(String[] args) {
		String email = "ixxxxxxxx@gmail.com", pass = "xxpppp", subject = "Java Mail", message = "This is a mail from Imtiaz Adar by Java";
		getConnection("ikkkkkkkkk@gmail.com", subject, message, email, pass);
	}
}
