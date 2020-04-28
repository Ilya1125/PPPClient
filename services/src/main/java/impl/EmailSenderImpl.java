package impl;

import interfaces.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

import static utils.FileUtils.saveUploadFileLocally;

@Service
@Slf4j
public class EmailSenderImpl implements EmailSender {

  Properties prop = new Properties();
  Session session;

  {
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "587");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true");

    session =
        Session.getInstance(
            prop,
            new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(System.getenv("EMAIL"), System.getenv("PASSWORD"));
              }
            });
  }

  Message message = new MimeMessage(session);

  public void sendMail(String to, String subject, String text) throws MessagingException {

    buildMessage(to, subject, text);

    log.info("send email, to: {}, subject: {}, text: {}", to, subject, text);

    Transport.send(message);
  }

  public void sendMessageWithAttachment(String to, String subject, String text, MultipartFile file)
      throws MessagingException, IOException {

    buildMessage(to, subject, text);

    Multipart multipart = new MimeMultipart();
    MimeBodyPart attachPart = new MimeBodyPart();
    attachPart.attachFile(saveUploadFileLocally(file));
    multipart.addBodyPart(attachPart);
    message.setContent(multipart);

    log.info(
        "send email with attachment {}, to: {}, subject: {}, text: {}",
        file.getOriginalFilename(),
        to,
        subject,
        text);

    Transport.send(message);
  }

  private void buildMessage(String to, String subject, String text) throws MessagingException {
    message.setFrom(new InternetAddress(System.getenv("EMAIL")));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
    message.setSubject(subject);
    message.setText(text);
  }
}
