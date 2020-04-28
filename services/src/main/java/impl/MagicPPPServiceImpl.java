package impl;

import exceptions.InvalidDataException;
import interfaces.EmailSender;
import interfaces.MagicPPPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
@Slf4j
public class MagicPPPServiceImpl implements MagicPPPService {

  @Autowired public EmailSender emailSender;

  private static final String MAGIC_PPP_EMAIL = "magicppp@gmv.com";

  @Override
  public void process(String processType, MultipartFile uploadFile) throws MessagingException, IOException {
    log.info("processType: {}", processType);
    log.info("file: {}", uploadFile.getOriginalFilename());

    if (!uploadFile
        .getOriginalFilename()
        .split("\\.")[1]
        .matches("[0-9][0-9][odnglphbmcODNGLPHBMC]")) {
      throw new InvalidDataException("File format isn't correct");
    }

    emailSender.sendMessageWithAttachment(
        MAGIC_PPP_EMAIL, processType == null ? "Static" : processType, "", uploadFile);

    log.info("Email sends");
  }
}
