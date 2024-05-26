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


}

