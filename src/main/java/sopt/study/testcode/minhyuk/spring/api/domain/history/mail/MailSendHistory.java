package sopt.study.testcode.minhyuk.spring.api.domain.history.mail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.study.testcode.minhyuk.spring.api.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MailSendHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String fromMail;
	private String toMail;
	private String subject;
	private String content;

	@Builder
	public MailSendHistory( String fromMail, String toMail, String subject, String content) {
		this.fromMail = fromMail;
		this.toMail = toMail;
		this.subject = subject;
		this.content = content;
	}

}
