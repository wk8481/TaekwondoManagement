package be.kdg.programming3.projectwilliamkasasa.presentation.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain( final HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(
                        auths -> auths
                                .requestMatchers(regexMatcher("^/(student\\?.+|students|techniques|technique\\?.+|search-students)"),
                                        regexMatcher(HttpMethod.GET, "^/login\\?.*"),
                                        regexMatcher(HttpMethod.GET, "^/error"))
                                     .permitAll()
                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/js/**"),
                                        antMatcher(HttpMethod.GET, "/css/**"),
                                        antMatcher(HttpMethod.GET, "/webjars/**"),
                                        regexMatcher(HttpMethod.GET, "\\.ico$"))
                                    .permitAll()
                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/api/**"),
                                        antMatcher(HttpMethod.POST, "/api/students")) // Specifically allowing this for the separate client
                                     .permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/"))
                                     .permitAll()
                                .anyRequest()
                                   .authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        antMatcher(HttpMethod.POST, "/api/students") //added this line allowing to be permitted for the client to access the api, so that this new endpoint won't need to
                ))
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(
                                (request, response, exception) -> {
                                    if (request.getRequestURI().startsWith("/api")) {
                                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                        } if (request.getRequestURI().startsWith("/error")) {
                                            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                                    } else {
                                        response.sendRedirect(request.getContextPath() + "/login");
                                    }
                                })
                );
//


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

