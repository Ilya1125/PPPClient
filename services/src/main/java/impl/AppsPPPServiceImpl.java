package impl;

import dto.FtpDto;
import exceptions.InvalidDataException;
import interfaces.AppsPPPService;
import interfaces.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.net.ftp.FTPClient;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class AppsPPPServiceImpl implements AppsPPPService {

  @Autowired public EmailSender emailSender;

  private static final String APPS_PPP_EMAIL = "appsrequest@gdgps.net";
  private static final String DEFAULT_FTP_ADDRESS = "ftp.ed.ac.uk";
  private static final String DEFAULT_FTP_USERNAME = "anonymous";
  private static final String DEFAULT_FTP_PASSWORD = "i.pestov1999@mail.ru";

  @Override
  public void process(String processType, FtpDto ftp, MultipartFile uploadFile)
      throws MessagingException {
    log.info("processType: {}", processType);
    log.info("ftp: {}", ftp.toString());
    log.info("file: {}", uploadFile.getOriginalFilename());

    if (!uploadFile
        .getOriginalFilename()
        .split("\\.")[1]
        .matches("[0-9][0-9][odnglphbmcODNGLPHBMC]")) {
      throw new InvalidDataException("File format isn't correct");
    }

    uploadFileToFTP(ftp, uploadFile);
    emailSender.sendMail(
        APPS_PPP_EMAIL,
        processType == null ? "Static" : processType,
        ftp.getFtpAddress() == null
            ? "ftp://" + DEFAULT_FTP_ADDRESS + "/incoming/" + uploadFile.getOriginalFilename()
            : "ftp://" + ftp.getFtpAddress() + "/incoming/" + uploadFile.getOriginalFilename());
    log.info("Email sends");
  }

  private void uploadFileToFTP(FtpDto ftp, MultipartFile uploadFile) {
    String server;
    String user;
    String pass;
    if (ftp.getFtpAddress() == null || ftp.getUsername() == null || ftp.getPassword() == null) {
      server = DEFAULT_FTP_ADDRESS;
      user = DEFAULT_FTP_USERNAME;
      pass = DEFAULT_FTP_PASSWORD;
    } else {
      server = ftp.getFtpAddress();
      user = ftp.getUsername();
      pass = ftp.getPassword();
    }
    FTPClient client = new FTPClient();
    try (InputStream is = uploadFile.getInputStream()) {
      client.connect(server);
      client.login(user, pass);
      client.changeWorkingDirectory("/incoming");
      client.storeFile(uploadFile.getOriginalFilename(), is);
      client.logout();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        client.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
