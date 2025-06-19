package sopt.study.testcode.jaeheon.spring.api.service.mail;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.jaeheon.spring.client.MailSendClient;
import sopt.study.testcode.jaeheon.spring.domain.history.mail.MailSendHistory;
import sopt.study.testcode.jaeheon.spring.domain.history.mail.MailSendHistoryRepository;
import sopt.study.testcode.jaeheon.spring.domain.order.OrderRepository;

@RequiredArgsConstructor
@Service
public class MailService {

	private final MailSendClient mailSendClient;
	private final MailSendHistoryRepository mailSendHistoryRepository;

	public boolean sendMail(String fromEmail, String toEmail, String subject, String content){

		boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);

		if(result){
			mailSendHistoryRepository.save(MailSendHistory.builder()
				.fromEmail(fromEmail)
				.toEmail(toEmail)
				.subject(subject)
				.content(content)
				.build()
			);

			mailSendClient.a();
			mailSendClient.b();
			mailSendClient.c();

			return true;
		}

		return false;
	};

}
