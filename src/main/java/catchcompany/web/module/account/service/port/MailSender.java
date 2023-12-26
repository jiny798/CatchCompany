package catchcompany.web.module.account.service.port;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

public interface MailSender {
	void send(String email, String title, String content);

	void send(SimpleMailMessage simpleMessage);
}
