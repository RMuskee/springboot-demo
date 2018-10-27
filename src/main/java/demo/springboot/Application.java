package demo.springboot;

import demo.springboot.model.Product;
import demo.springboot.model.Role;
import demo.springboot.model.User;
import demo.springboot.repository.ProductRepository;
import demo.springboot.repository.RoleRepository;
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

import java.util.*;

@SpringBootApplication
public class Application implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner fillUserData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {
        return args -> {
            Role roleReader = new Role("ROLE_USER");
            Role roleEditor = new Role("ROLE_EDITOR");
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleRepository.save(roleReader);
            roleRepository.save(roleEditor);
            roleRepository.save(roleAdmin);

            List<Role> user1Roles = new ArrayList<>();
            user1Roles.add(roleReader);
            List<Role> user2Roles = new ArrayList<>();
            user2Roles.add(roleEditor);
            List<Role> user3Roles = new ArrayList<>();
            user3Roles.add(roleEditor);
            user3Roles.add(roleAdmin);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User john = new User("john", passwordEncoder.encode("john"), user1Roles);
            User mike = new User("mike", passwordEncoder.encode("mike"), user2Roles);
            User bill = new User("bill", passwordEncoder.encode("bill"), user3Roles);
            userRepository.save(john);
            userRepository.save(mike);
            userRepository.save(bill);

            Product product = new Product();
            product.setProductName("Duct tape");
            product.setDescription("Cool stuff!");

            Product product2 = new Product();
            product2.setProductName("Race car");
            product2.setDescription("Even cooler stuff!");

            productRepository.save(product);
            productRepository.save(product2);
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
