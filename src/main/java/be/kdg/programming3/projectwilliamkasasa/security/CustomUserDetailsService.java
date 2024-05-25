package be.kdg.programming3.projectwilliamkasasa.security;

import be.kdg.programming3.projectwilliamkasasa.service.InstructorService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final InstructorService instructorService;

    public CustomUserDetailsService(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // My own `User` (the entity)
        var instructor = instructorService.getUserByName(username);
        if (instructor != null) {
            var authorities = new ArrayList<SimpleGrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(instructor.getRole().getCode()));
            return new CustomUserDetails(
                    instructor.getUsername(),
                    instructor.getPassword(),
                    authorities,
                    instructor.getId());
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }
}
