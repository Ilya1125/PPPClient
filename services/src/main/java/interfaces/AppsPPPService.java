package interfaces;

import dto.FtpDto;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

public interface AppsPPPService {
    void process(String processType, FtpDto ftpAddress, MultipartFile uploadFile) throws MessagingException;
}
