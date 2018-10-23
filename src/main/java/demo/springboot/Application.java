package demo.springboot;

import demo.springboot.model.Role;
import demo.springboot.model.User;
import demo.springboot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@SpringBootApplication
public class Application implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner fillUserData(UserRepository userRepository) {
        return args -> {
            Role roleReader = new Role("READER");
            Role roleEditor = new Role("EDITOR");
            Role roleAdmin = new Role("ADMIN");

            Set<Role> user1Roles = new HashSet<>();
            user1Roles.add(roleReader);
            Set<Role> user2Roles = new HashSet<>();
            user2Roles.add(roleEditor);
            Set<Role> user3Roles = new HashSet<>();
            user3Roles.add(roleAdmin);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User userReader = new User("reader", passwordEncoder.encode("reader"), user1Roles);
            User userEditor = new User("editor", passwordEncoder.encode("editor"), user2Roles);
            User userAdmin = new User("admin", passwordEncoder.encode("admin"), user3Roles);
            userRepository.save(userReader);
            userRepository.save(userEditor);
            userRepository.save(userAdmin);
        };
    }

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return  localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }
}
