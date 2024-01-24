package catchcompany.web.module.account.service;

import java.util.Collections;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import catchcompany.web.module.account.controller.form.SignUpForm;
import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.account.infra.repository.AccountJpaRepository;
import catchcompany.web.module.account.service.port.MailSender;
import catchcompany.web.module.common.service.port.ClockHolder;
import catchcompany.web.module.common.service.port.TokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
	private final ClockHolder clockHolder;
	private final AccountJpaRepository accountRepository;
	private final TokenGenerator tokenGenerator;
	private final MailSender mailSender;
	private final PasswordEncoder passwordEncoder;
	private final SecurityContextHolderStrategy securityContextHolderStrategy;
	private final SecurityContextRepository securityContextRepository;

	public void login(Account account, HttpServletRequest request, HttpServletResponse response) {
		account.login(clockHolder);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
			account.getNickname(),
			account.getPassword(),
			Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
		SecurityContext context = securityContextHolderStrategy.createEmptyContext();
		context.setAuthentication(token);
		securityContextHolderStrategy.setContext(context);
		securityContextRepository.saveContext(context, request, response);
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
		mailMessage.setText(
			String.format("/account/check-email-token?token=%s&email=%s", newAccount.getEmailAuthToken(),
				newAccount.getEmail()));
		mailSender.send(mailMessage);
	}

	public void certificate(Account account, String emailAuthToken) {
		account.certificate(emailAuthToken);
	}

}
