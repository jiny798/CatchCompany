package catchcompany.web.module.account.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import catchcompany.web.module.account.controller.form.SignUpForm;
import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.account.infra.repository.AccountJpaRepository;
import catchcompany.web.module.account.service.port.MailSender;
import catchcompany.web.module.common.service.port.ClockHolder;
import catchcompany.web.module.common.service.port.TokenGenerator;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
	private final ClockHolder clockHolder;
	private final AccountJpaRepository accountRepository;
	private final TokenGenerator tokenGenerator;
	private final MailSender mailSender;
	private final PasswordEncoder passwordEncoder;

	public void login(Account account) {
		account.login(clockHolder);
	}

	public void signUp(SignUpForm form) {
		form.setPassword(passwordEncoder.encode(form.getPassword()));
		Account newAccount = accountRepository.save(Account.from(form, tokenGenerator));
		sendAuthEmail(newAccount);
	}

	public void sendAuthEmail(Account newAccount) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(newAccount.getEmail());
		mailMessage.setSubject("회원 가입 인증");
		mailMessage.setText(String.format("/account/email-auth?token=%s&email=%s", newAccount.getEmailAuthToken(),
			newAccount.getEmail()));
		mailSender.send(mailMessage);
	}

	public void certificate(Account account, String emailAuthToken) {
		account.certificate(emailAuthToken);
	}

}
