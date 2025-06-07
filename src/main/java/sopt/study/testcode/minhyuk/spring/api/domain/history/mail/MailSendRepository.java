package sopt.study.testcode.minhyuk.spring.api.domain.history.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailSendRepository extends JpaRepository<MailSendHistory, Long> {
}
