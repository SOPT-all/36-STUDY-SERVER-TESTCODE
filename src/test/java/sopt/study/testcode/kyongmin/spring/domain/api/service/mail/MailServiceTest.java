package sopt.study.testcode.kyongmin.spring.domain.api.service.mail;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import sopt.study.testcode.kyongmin.spring.client.mail.MailSendClient;
import sopt.study.testcode.kyongmin.spring.domain.history.mail.MailSendHistory;
import sopt.study.testcode.kyongmin.spring.domain.history.mail.MailSendHistoryRepository;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

	@Mock
	private MailSendClient mailSendClient;

	@Mock
	private MailSendHistoryRepository mailSendHistoryRepository;

	@InjectMocks
	private MailService mailService;

	@Test
	@DisplayName("메일 전송 테스트")
	void sendMail() {
		// given
		// doReturn(true)
		// 		.when(mailSendClient)
		// 		.sendEmail(anyString(), anyString(), anyString(), anyString());

		given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
				.willReturn(true);

		// when
		boolean result = mailService.sendMail("", "", "", "");

		// then
		assertThat(result).isTrue();
		verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
	}

}