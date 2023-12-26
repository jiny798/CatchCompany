package catchcompany.web.module.account.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import catchcompany.web.module.account.controller.dto.SignUpForm;
import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.account.domain.entity.AccountCreate;
import catchcompany.web.module.account.service.port.AccountRepository;
import catchcompany.web.module.account.service.port.MailSender;
import catchcompany.web.module.common.service.port.ClockHolder;
import catchcompany.web.module.common.service.port.TokenGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final ClockHolder clockHolder;
	private final AccountRepository accountRepository;
	private final TokenGenerator tokenGenerator;
	private final MailSender mailSender;

	public void login(Account account) {
		account.login(clockHolder);
	}

	public void signUp(SignUpForm form) {
		Account account = Account.from(form, tokenGenerator);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(account.getEmail());
		mailMessage.setSubject("회원 가입 인증");
		mailMessage.setText(String.format("/email-auth?token=%s&email=%s", account.getEmailAuthToken(),
			account.getEmail()));
		mailSender.send(mailMessage);
		accountRepository.save(account);
	}
}