package sopt.study.testcode.yeongju.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TestCodeStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCodeStudyApplication.class, args);
	}

}
