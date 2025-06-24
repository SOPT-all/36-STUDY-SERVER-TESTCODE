package sopt.study.testcode.yerin.cafekiosk.spring.api.service.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import sopt.study.testcode.yerin.cafekiosk.spring.client.mail.MailSendClient;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

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
    void sendMail() {
        // given
//        Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);
        given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true); //mockito를 한번 감싼 것 (기능 동일)

//        doReturn(true)
//                .when(mailSendClient)
//                .sendEmail(anyString(), anyString(), anyString(), anyString());

        // when
        boolean result = mailService.sendMail("","","","");

        // then
        assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }
}