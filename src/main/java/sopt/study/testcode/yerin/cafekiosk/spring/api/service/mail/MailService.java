package sopt.study.testcode.yerin.cafekiosk.spring.api.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.study.testcode.yerin.cafekiosk.spring.client.mail.MailSendClient;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

@RequiredArgsConstructor
@Service
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);
        if(result) {
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
