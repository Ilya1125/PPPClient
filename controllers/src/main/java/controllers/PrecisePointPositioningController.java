package controllers;

import dto.FtpDto;
import interfaces.AppsPPPService;
import interfaces.CsrsPPPService;
import interfaces.MagicPPPService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@Slf4j
@Validated
public class PrecisePointPositioningController {

  @Autowired MagicPPPService magicPPPService;
  @Autowired AppsPPPService appsPPPService;
  @Autowired CsrsPPPService csrsPPPService;

  @PostMapping("/magicppp")
  public ResponseEntity<?> process(
      @RequestParam MultipartFile uploadFile,
      @ApiParam(defaultValue = "Static", allowableValues = "Static, Kinematic")
        @RequestParam String processType)
      throws MessagingException {
    log.info("magicPPP, processType: {}", processType);
    if (uploadFile.isEmpty()) {
      return new ResponseEntity<>("please select a file!", HttpStatus.OK);
    }

    magicPPPService.process(processType, uploadFile);

    return new ResponseEntity<>(
        "File " + uploadFile.getOriginalFilename() + " successfully send",
        new HttpHeaders(),
        HttpStatus.OK);
  }

  @PostMapping("/apps")
  public ResponseEntity<?> process(
      @RequestParam MultipartFile uploadFile,
      @ApiParam(defaultValue = "Static", allowableValues = "Static, Kinematic")
        @RequestParam String processType,
      @RequestParam(required = false) String ftpAddress,
      @RequestParam(required = false) String username,
      @RequestParam(required = false) String password)
      throws MessagingException {
    log.info("magicPPP, processType: {}, ftpAddress: {}", processType, ftpAddress);
    if (uploadFile.isEmpty()) {
      return new ResponseEntity<>("please select a file!", HttpStatus.OK);
    }
    appsPPPService.process(processType, new FtpDto(ftpAddress, username, password), uploadFile);
    return new ResponseEntity<>(
        "File " + uploadFile.getOriginalFilename() + " successfully send",
        new HttpHeaders(),
        HttpStatus.OK);
  }

  @PostMapping("/csrs")
  public ResponseEntity<?> process(
      @RequestParam @NotEmpty String csrsUsername,
      @RequestParam(required = false) String emailToSend,
      @ApiParam(defaultValue = "Static", allowableValues = "Static, Kinematic")
          @RequestParam(required = false)
          String processType,
      @RequestParam MultipartFile uploadFile)
      throws IOException, InterruptedException {

    log.info(
        "csrs, csrsUsername: {}, emailToSend: {}, processType: {}",
        csrsUsername,
        emailToSend,
        processType);
    if (uploadFile.isEmpty()) {
      return new ResponseEntity<>("please select a file!", HttpStatus.OK);
    }
    csrsPPPService.process(csrsUsername, emailToSend, processType, uploadFile);
    return new ResponseEntity<>(
        "File " + uploadFile.getOriginalFilename() + " successfully send",
        new HttpHeaders(),
        HttpStatus.OK);
  }

  @PostMapping("/customPPPService")
  public ResponseEntity<?> process(@RequestParam MultipartFile uploadFile)
         {
    log.info(
            "customPPPService");
    return new ResponseEntity<>(
            new HttpHeaders(),
            HttpStatus.NOT_IMPLEMENTED);
  }
}
