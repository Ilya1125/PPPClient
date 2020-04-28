package interfaces;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MagicPPPService {
    void process(String processType, MultipartFile uploadFile) throws MessagingException, IOException;
}
