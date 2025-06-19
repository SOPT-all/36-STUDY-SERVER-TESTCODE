package sopt.study.testcode.jaeheon.spring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import sopt.study.testcode.jaeheon.spring.client.MailSendClient;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {
	@MockitoBean
	protected MailSendClient mailSendClient;
}
