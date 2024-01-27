package catchcompany.web.module.account.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import catchcompany.web.module.account.service.port.MailSender;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String email;

	@Override
	public void send(String email, String title, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject(title);
		message.setText(content);
		message.setFrom(email);
		javaMailSender.send(message);
	}

	@Override
	public void send(SimpleMailMessage message) {
		message.setFrom(email);
		javaMailSender.send(message);
	}
}
