package entities;

import dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private int userId;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    public UserDto convertToDto() {
        return new UserDto(userId, login, role, password, email);
    }
}
