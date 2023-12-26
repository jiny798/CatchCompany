package catchcompany.web.module.account.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import catchcompany.web.module.account.service.port.AccountRepository;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	AccountRepository accountRepository;
	@MockBean
	JavaMailSender mailSender;

	@Test
	@DisplayName("회원 가입 정상 처리")
	void 회원가입이_정상_처리된다() throws Exception {
		mockMvc.perform(post("/account/sign-up")
				.param("nickname", "jiny798")
				.param("email", "jiny798@email.com")
				.param("password", "jiny798!")
				.param("confirmPassword", "jiny798!")
				.with(csrf()))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));

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
}