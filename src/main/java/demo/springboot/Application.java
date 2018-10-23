package demo.springboot;

import demo.springboot.model.Article;
import demo.springboot.model.Role;
import demo.springboot.model.User;
import demo.springboot.repository.ArticleRepository;
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
    public CommandLineRunner fillUserData(UserRepository userRepository, ArticleRepository articleRepository) {
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
            User john = new User("john", passwordEncoder.encode("john"), user1Roles);
            User mike = new User("mike", passwordEncoder.encode("mike"), user2Roles);
            User bill = new User("bill", passwordEncoder.encode("bill"), user3Roles);
            userRepository.save(john);
            userRepository.save(mike);
            userRepository.save(bill);

            Article article1 = new Article(john, "Good movies", "Here some elaborate article about the subject.");
            Article article2 = new Article(john, "Even better movies", "Here some elaborate article about the subject.");
            Article article3 = new Article(bill, "Great musical performances", "Here some elaborate article about the subject.");

            articleRepository.save(article1);
            articleRepository.save(article2);
            articleRepository.save(article3);
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
