package interfaces;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

public interface EmailSender {

    void sendMail(String to, String subject, String text) throws MessagingException;
    void sendMessageWithAttachment(String to, String subject, String text, MultipartFile file) throws MessagingException;
}
