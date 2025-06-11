package sopt.study.testcode.jaeheon.spring.client;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailSendClient {

	public boolean sendEmail(String fromEmail, String toEmail, String subject, String content){
		log.info("메일 전송");
		throw new IllegalArgumentException("메일 전송"); // 외부 네트워크 작업을 사용하는
		// return false;
	}

	public void a(){
		log.info("a");
	}

	public void b(){
		log.info("b");
	}

	public void c(){
		log.info("c");
	}
}
