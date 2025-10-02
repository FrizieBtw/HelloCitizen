package org.example.hellocitizen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.hellocitizen", "dao", "service", "controller"})
@EntityScan(basePackages = {"entity"})
public class HelloCitizenApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloCitizenApplication.class, args);
    }

}
