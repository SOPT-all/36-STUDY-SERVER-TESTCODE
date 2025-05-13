package sopt.study.testcode.hyeeum.cafekiosk.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CafeKioskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeKioskApplication.class, args);
    }

}
