package be.kdg.programming3.projectwilliamkasasa.presentation.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MvcConfig  {

    @Bean
    public MessageSource messageSource() {
        // ReloadableResourceBundleMessageSource is used to load the messages.properties file
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("home"); // Name of the file
        messageSource.setDefaultEncoding("UTF-8"); // Encoding of the file
        return messageSource;
    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH); // Default locale
        slr.setLocaleAttributeName("session.current.locale"); // Name of the session attribute that holds the Locale
        slr.setTimeZoneAttributeName("session.current.timezone"); // Name of the session attribute that holds the TimeZone
        return slr;
    }


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//        lci.setParamName("lang"); // Parameter name in the URL to switch language
//        registry.addInterceptor(lci);
//    }





}