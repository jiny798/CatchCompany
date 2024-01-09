package catchcompany.web.module.account.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import catchcompany.web.module.account.controller.dto.SignUpForm;
import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.account.domain.entity.AccountCreate;
import catchcompany.web.module.account.service.port.AccountRepository;
import catchcompany.web.module.account.service.port.MailSender;
import catchcompany.web.module.common.service.port.ClockHolder;
import catchcompany.web.module.common.service.port.TokenGenerator;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
	private final ClockHolder clockHolder;
	private final AccountRepository accountRepository;
	private final TokenGenerator tokenGenerator;
	private final MailSender mailSender;
	private final PasswordEncoder passwordEncoder;

	public void login(Account account) {
		account.login(clockHolder);
	}

	public void signUp(SignUpForm form) {
		form.setPassword(passwordEncoder.encode(form.getPassword()));
		Account account = Account.from(form, tokenGenerator);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(account.getEmail());
		mailMessage.setSubject("회원 가입 인증");
		mailMessage.setText(String.format("/account/email-auth?token=%s&email=%s", account.getEmailAuthToken(),
			account.getEmail()));
		mailSender.send(mailMessage);
		accountRepository.save(account);
	}

	public void certificate(Account account, String emailAuthToken) {
		account.certificate(emailAuthToken);
	}

}
