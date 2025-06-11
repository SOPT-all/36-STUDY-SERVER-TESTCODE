package sopt.study.testcode.jaeheon.spring.api.service.mail;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import sopt.study.testcode.jaeheon.spring.client.MailSendClient;
import sopt.study.testcode.jaeheon.spring.domain.history.mail.MailSendHistory;
import sopt.study.testcode.jaeheon.spring.domain.history.mail.MailSendHistoryRepository;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

	@Mock
	private MailSendClient mailSendClient;
	@Mock
	private MailSendHistoryRepository mailSendHistoryRepository;
	@InjectMocks
	private MailService mailService;

	@DisplayName("메일 전송 테스트")
	@Test
	void test(){
	    // given
		// Spy는 실제 객체를 기반으로 만들어지므로 when 메서드를 사용 불가함.
		// Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), any(String.class), any(String.class)))
		// 	.thenReturn(true);
		
		given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
			.willReturn(true);

		// doReturn(true)
		// 	.when(mailSendClient)
		// 	.sendEmail(anyString(), anyString(), anyString(), anyString());



		// when
		boolean result = mailService.sendMail("", "", "", "");

		// then
		assertThat(result).isTrue();

		// MailSendHistory 타입 any가 들어왔을 때 save라는 메서드가 한번만 호출됐는지 확인하는 것.
		verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
	}

}