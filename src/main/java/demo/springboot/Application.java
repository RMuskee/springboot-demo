package demo.springboot;

import demo.springboot.model.Role;
import demo.springboot.model.User;
import demo.springboot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {
            Set<Role> user1Roles = new HashSet<>();
            user1Roles.add(new Role("USER"));
            Set<Role> user2Roles = new HashSet<>();
            user2Roles.add(new Role("ADMIN"));
            User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", user1Roles);
            User user2 = new User("admin", "$2a$08$bCCcGjB03eulCWt3CY0AZew2rVzXFyouUolL5dkL/pBgFkUH9O4J2", user2Roles);
            userRepository.save(user1);
            userRepository.save(user2);
        };
    }
}
