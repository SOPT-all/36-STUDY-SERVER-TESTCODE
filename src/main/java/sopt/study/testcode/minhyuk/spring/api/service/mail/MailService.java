package sopt.study.testcode.minhyuk.spring.api.service.mail;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.minhyuk.spring.api.client.mail.MailClient;
import sopt.study.testcode.minhyuk.spring.api.domain.history.mail.MailSendHistory;
import sopt.study.testcode.minhyuk.spring.api.domain.history.mail.MailSendRepository;

@Service
@RequiredArgsConstructor
public class MailService {

	private final MailClient mailClient;
	private final MailSendRepository mailSendRepository;

	public boolean sendMail(String fromMail, String toMail, String subject, String content){

		boolean result = mailClient.mailSend(fromMail,toMail,subject,content);

		if(result){
			//기록용 엔티티를 저장하는 것이다.
			mailSendRepository.save(MailSendHistory.builder()
					.fromMail(fromMail)
					.toMail(toMail)
					.subject(subject)
					.content(content)
				.build());
			return true;
		}
		return false;
	}
}
