package interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CsrsPPPService {
    void process(String csrsUsername, String emailToSend, String processType, MultipartFile uploadFile) throws IOException, InterruptedException;
}
