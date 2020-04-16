package impl;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static String encryptedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        return encoder.encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        String roleNames = user.getRole();
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNames != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority(roleNames);
            grantList.add(authority);
        }

        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                encryptedPassword(user.getPassword()), grantList);
    }
}
