package impl;

import dto.FtpDto;
import exceptions.InvalidDataException;
import interfaces.AppsPPPService;
import interfaces.CsrsPPPService;
import interfaces.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CsrsPPPServiceImpl implements CsrsPPPService {

  @Override
  public void process(
      String csrsUsername,
      String emailToSend,
      String processType,
      MultipartFile uploadFile)
          throws IOException, InterruptedException {
    log.info("csrsUsername: {}", csrsUsername);
    log.info("emailToSend: {}", emailToSend);
    log.info("processType: {}", processType);
    log.info("file: {}", uploadFile.getOriginalFilename());

    if(!uploadFile.getOriginalFilename().split("\\.")[1].matches("[0-9][0-9][odnglphbmcODNGLPHBMC]")){
      throw new InvalidDataException("File format isn't correct");
    }

    List<String> command =
        new ArrayList<>(
            Arrays.asList(
                "python3",
                "/home/ilya/Downloads/rinex/csrs_ppp_auto_v1_4_0.py",
                "--user_name",
                csrsUsername,
                "--rnx",
                saveUploadFileLocally(uploadFile),
                    //"/home/ilya/Downloads/rinex/" + uploadFile.getOriginalFilename(),
                "--mode",
                processType == null ? "Static" : processType,
                "--email",
                emailToSend == null ? csrsUsername : emailToSend));

    log.info(String.valueOf(command));

    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command(command);
    Process process = processBuilder.start();

    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

    String line;
    while ((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    int exitCode = process.waitFor();
    System.out.println("\nExited with error code : " + exitCode);
  }

  private String saveUploadFileLocally(MultipartFile uploadFile) throws IOException {
    File temp_file = new File("/home/ilya/IdeaProjects/PPPClient/uploadedFiles" + uploadFile.getOriginalFilename());
    uploadFile.transferTo(temp_file);

    return temp_file.getAbsolutePath();
  }
}

