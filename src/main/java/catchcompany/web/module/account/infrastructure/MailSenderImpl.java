package catchcompany.web.module.account.infrastructure;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import catchcompany.web.module.account.service.port.MailSender;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

	private final JavaMailSender javaMailSender;

	@Override
	public void send(String email, String title, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject(title);
		message.setText(content);
		javaMailSender.send(message);
	}

	@Override
	public void send(SimpleMailMessage message) {
		javaMailSender.send(message);
	}
}
