package sopt.study.testcode.minhyuk.spring.api.service.mail;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import sopt.study.testcode.minhyuk.spring.api.client.mail.MailClient;
import sopt.study.testcode.minhyuk.spring.api.domain.history.mail.MailSendHistory;
import sopt.study.testcode.minhyuk.spring.api.domain.history.mail.MailSendRepository;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

	@Mock
	private MailClient mailClient;

	@Mock
	private MailSendRepository mailSendRepository;

	@InjectMocks
	private MailService mailService;

	@DisplayName("메일 전송 테스트")
	@Test
	void test(){
	    //given
/*		MailClient mailClient = Mockito.mock(MailClient.class);
		MailSendRepository mailSendHistoryRepository = Mockito.mock(MailSendRepository.class);*/


		// MailService mailService = new MailService(mailClient,mailSendRepository);
		when(mailClient.mailSend(any(String.class),any(String.class),any(String.class),any(String.class)))
			.thenReturn(true);



		//when
		boolean result = mailService.sendMail("","","","");



	    //then
		assertThat(result).isTrue();
		Mockito.verify(mailSendRepository,times(1)).save(any(MailSendHistory.class));

	 }

}