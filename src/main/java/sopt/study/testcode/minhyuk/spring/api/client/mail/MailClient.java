package sopt.study.testcode.minhyuk.spring.api.client.mail;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailClient {

	public boolean mailSend(String fromMail, String toMail, String subject, String content){
		//실제 메일 전송

		log.info("메일 전송");
		throw new IllegalArgumentException("메일 전송 실패");
		// return true;

	}



}
