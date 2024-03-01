package be.kdg.programming3.projectwilliamkasasa.presentation.config;

import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers.PageVisitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class SessionConfig implements WebMvcConfigurer {

    //add the page visit interceptor first as a bean then and save as object them take it and  the interceptor registry which will be saved by spring
    @Bean
    public PageVisitInterceptor pageVisitInterceptor() {
        return new PageVisitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pageVisitInterceptor());
    }
}

