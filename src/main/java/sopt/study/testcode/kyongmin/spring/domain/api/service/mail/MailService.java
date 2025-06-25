package sopt.study.testcode.kyongmin.spring.domain.api.service.mail;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sopt.study.testcode.kyongmin.spring.client.mail.MailSendClient;
import sopt.study.testcode.kyongmin.spring.domain.history.mail.MailSendHistory;
import sopt.study.testcode.kyongmin.spring.domain.history.mail.MailSendHistoryRepository;

@Service
@RequiredArgsConstructor
public class MailService {

	private final MailSendClient mailSendClient;
	private final MailSendHistoryRepository mailSendHistoryRepository;

	public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
		boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);
		if (result) {
			mailSendHistoryRepository.save(MailSendHistory.builder()
					.fromEmail(fromEmail)
					.toEmail(toEmail)
					.subject(subject)
					.content(content)
					.build()
			);
			return true;
		}

		return false;
	}

}