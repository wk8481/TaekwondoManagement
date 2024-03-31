package be.kdg.programming3.projectwilliamkasasa.presentation.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeHttpRequests(
                        auths -> auths
                                .requestMatchers(regexMatcher("^/(student\\?.+|students|techniques|technique\\?.+|search-students)"))
                                .permitAll()
                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/js/**"),
                                        antMatcher(HttpMethod.GET, "/css/**"),
                                        antMatcher(HttpMethod.GET, "/webjars/**"),
                                        regexMatcher(HttpMethod.GET, "\\.ico$"))
                                .permitAll()
                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/api/**"))
                                .permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/"))
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(
                                (request, response, exception) -> {
                                    if (request.getRequestURI().startsWith("/api")) {
                                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                    } else {
                                        response.sendRedirect(request.getContextPath() + "/login");
                                    }
                                })
                );
//                .csrf(csrf -> csrf.disable());

        // @formatter:on
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

