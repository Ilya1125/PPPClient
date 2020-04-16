package dto;

import entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import validation.Role;

import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
  private int userId;

  @NotBlank(message = "Login required")
  private String login;

  @NotBlank(message = "Role required")
  @Role
  private String role;

  @NotBlank
  @Size(min = 6, message = "Password should have at least 6 characters")
  private String password;

  @NotBlank
  @Size(min = 2, message = "Email required")
  @Email
  private String email;

  @Override
  public String toString() {
    return "User{"
            + "ID="
            + userId
            + ", email='"
            + email
            + '\''
            + '}';
  }

  public User convertToEntity() {
    return new User(userId, login, role, password, email);
  }
}
