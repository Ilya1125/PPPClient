package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FtpDto {
    private String ftpAddress;
    private String username;
    private String password;
}
