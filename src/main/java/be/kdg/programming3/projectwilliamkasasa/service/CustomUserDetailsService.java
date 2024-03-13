package be.kdg.programming3.projectwilliamkasasa.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // My own `User` (the entity)
        var user = userService.getUserByName(username);
        if (user != null) {
            var authorities = new ArrayList<SimpleGrantedAuthority>();
            // Spring's `User`
            return new User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities);
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }
}
