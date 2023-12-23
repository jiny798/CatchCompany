package catchcompany.web.module.account.service;

import org.springframework.stereotype.Service;

import catchcompany.web.module.account.service.port.MailSender;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificationService {
	private final MailSender mailSender;

	public void send(String email, long userId, String certificationCode) {
		String certificationUrl = generateCertificationUrl(userId, certificationCode);
		String title = "이메일 인증 주소 전달";
		String content = "인증을 위해 주소를 클릭해주세요 (본인이 아니라면 무시하면 됩니다) " + certificationUrl;
		mailSender.send(email, title, content);
	}

	private String generateCertificationUrl(long userId, String certificationCode) {
		return "http://localhost:8080/account/" + userId + "/verify?token=" + certificationCode;
	}
}
