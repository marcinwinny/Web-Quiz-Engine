package com.marcinwinny.engine;

import com.marcinwinny.engine.model.User;
import com.marcinwinny.engine.repository.QuizRepository;
import com.marcinwinny.engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class WebQuizEngine {

    public static void main(String[] args) {
        SpringApplication.run(WebQuizEngine.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner quizDemo2(QuizRepository quizRepository, UserRepository userRepository) {
        return args -> {
//            userRepository.deleteAll();
            System.out.println("Total quizzes in DB:");
            System.out.println("------------------");
            System.out.println(quizRepository.count());
            System.out.println();

            System.out.println("Total users in DB:");
            System.out.println("------------------");
            System.out.println(userRepository.count());
            System.out.println();

            String nameAdmin = "admin@admin.com";
            String passwordAdmin = "admin";

            if(userRepository.findByUsername(nameAdmin).isEmpty()){
                passwordAdmin = passwordEncoder.encode(passwordAdmin);
                User admin = new User(nameAdmin, passwordAdmin);
                admin.setRoles("ROLE_ADMIN");
                userRepository.save(admin);
            }

        };

    }



}