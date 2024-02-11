package be.kdg.programming3.projectwilliamkasasa.presentation.config;

import be.kdg.programming3.projectwilliamkasasa.presentation.converters.StringToTypeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

   //convert string to type enum
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToTypeConverter());
    }



    //change language

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // Parameter name in the URL to switch language
        return lci;  //http://localhost:8081/?lang=fr
    }

    //add the language local change interceptor to the interceptor registry which will be saved by spring
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
