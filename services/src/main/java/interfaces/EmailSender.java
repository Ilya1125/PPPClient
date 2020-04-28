package interfaces;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailSender {

    void sendMail(String to, String subject, String text) throws MessagingException;
    void sendMessageWithAttachment(String to, String subject, String text, MultipartFile file) throws MessagingException, IOException;
}
