package sopt.study.testcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "sopt.study.testcode.hyunjin.spring.domain")
@SpringBootApplication
public class TestcodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestcodeApplication.class, args);
    }

}
