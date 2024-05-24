package be.kdg.programming3.projectwilliamkasasa.security;

import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final int instructorId;

    public CustomUserDetails(String username,
                             String password,
                             Collection<? extends GrantedAuthority> authorities,
                             int userId) {
        super(username, password, authorities);
        this.instructorId = userId;
    }

    public int getInstructorId() {
        return instructorId;
    }

//    public boolean isAdmin() {
//        // Implement logic to determine if the user is an admin
//        // For example, check if the user has a specific role like "ADMIN"
//        return this.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
//    }
}

