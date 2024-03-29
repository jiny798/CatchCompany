package catchcompany.web.module.account.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.aspectj.lang.annotation.Before;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import catchcompany.web.module.account.controller.form.SignUpForm;
import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.account.infra.repository.AccountJpaRepository;
import catchcompany.web.module.account.service.port.MailSender;
import catchcompany.web.module.mock.MockTokenGenerator;
import catchcompany.web.module.security.SecurityConfig;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@Import({SecurityConfig.class})
class AccountControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	AccountJpaRepository accountRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@MockBean
	MailSender mailSender;

	@Test
	@DisplayName("회원 가입 폼 화면 진입")
	void 회원가입_화면_진입() throws Exception {
		mockMvc.perform(get("/account/sign-up"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("account/register"));
	}

	@Test
	@DisplayName("회원 가입 정상 처리")
	void 회원가입이_정상_처리된다() throws Exception {
		String password = "jiny798!";
		mockMvc.perform(post("/account/sign-up")
				.param("nickname", "jiny798")
				.param("email", "jiny798@email.com")
				.param("password", password)
				.param("confirmPassword", password)
				.with(csrf()))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/account/email-auth-send"));

		Account account = accountRepository.findByEmailAndIsValid("jiny798@email.com", false).get();
		assertNotEquals(account.getPassword(), password); // 암호화로 서로 불일치
		assertTrue(accountRepository.existsByEmail("jiny798@email.com"));

		then(mailSender).should().send(any(SimpleMailMessage.class));
	}

	@Test
	@DisplayName("회원 가입 오류 : 이메일 형식 불일치")
	void 이메일형식이_맞지_않으면_되돌아간다() throws Exception {
		mockMvc.perform(post("/account/sign-up")
				.param("nickname", "jiny798")
				.param("email", "jiny798email")
				.param("password", "jiny798!")
				.param("confirmPassword", "jiny798!")
				.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("account/register"));
	}

	@Test
	@DisplayName("회원 가입 오류 : 닉네임 형식 불일치")
	void 닉네임형식이_맞지_않으면_되돌아간다() throws Exception {
		mockMvc.perform(post("/account/sign-up")
				.param("nickname", "j")
				.param("email", "jiny798@email")
				.param("password", "jiny798!")
				.param("confirmPassword", "jiny798!")
				.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("account/register"));
	}

	@Test
	@DisplayName("회원 가입 오류 : 비밀번호 불일치")
	void 비밀번호가_다르면_되돌아간다() throws Exception {
		mockMvc.perform(post("/account/sign-up")
				.param("nickname", "jiny798")
				.param("email", "jiny798@email")
				.param("password", "jiny798!")
				.param("confirmPassword", "jiny798!!")
				.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("account/register"));
	}

	@Test
	@DisplayName("인증 메일 확인 : 링크 오류")
	void 잘못된_링크로_인증_메일을_확인할_경우_오류화면을_반환() throws Exception {
		mockMvc.perform(get("/account/check-email-token")
				.param("token", "123")
				.param("email", "email"))
			.andExpect(status().isOk())
			.andExpect(view().name("account/email-auth/email-verification"))
			.andExpect(model().attributeExists("error"));
	}

	@Test
	@DisplayName("인증 메일 확인 : 유효한 링크")
	@Transactional
	void verifyEmail() throws Exception {
		// given
		SignUpForm signUpForm = new SignUpForm(
			"nickname",
			"jiny798@catch.com",
			"jiny1234!",
			"jiny1234!"
		);
		signUpForm.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
		signUpForm.setConfirmPassword(passwordEncoder.encode(signUpForm.getConfirmPassword()));
		Account account = Account.from(signUpForm, new MockTokenGenerator("aaa-bbb-ccc"));
		Account newAccount = accountRepository.save(account);

		// when&then
		mockMvc.perform(get("/account/check-email-token")
				.param("token", newAccount.getEmailAuthToken())
				.param("email", newAccount.getEmail()))
			.andExpect(status().isOk())
			.andExpect(view().name("account/email-auth/email-verification"))
			.andExpect(model().attributeDoesNotExist("error"))
			.andExpect(model().attributeExists("nickname", "nickname"))
			.andExpect(authenticated().withUsername(newAccount.getNickname()));
	}

}