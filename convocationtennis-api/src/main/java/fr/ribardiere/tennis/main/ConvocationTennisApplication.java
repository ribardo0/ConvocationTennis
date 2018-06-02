package fr.ribardiere.tennis.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("fr.ribardiere.tennis")
@SpringBootApplication
public class ConvocationTennisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConvocationTennisApplication.class, args);

    }

}
