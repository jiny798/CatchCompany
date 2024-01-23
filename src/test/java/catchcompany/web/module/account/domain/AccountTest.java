package catchcompany.web.module.account.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import catchcompany.web.module.account.controller.form.SignUpForm;
import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.account.exception.AuthTokenNotMatchedException;
import catchcompany.web.module.mock.MockClockHolder;
import catchcompany.web.module.mock.MockTokenGenerator;

public class AccountTest {

	@Test
	public void UserCreate_객체로_생성할_수_있다() {
		// given
		SignUpForm signUpForm = new SignUpForm(
			"jyoung798@naver.com",
			"jiny",
			"abc123123!",
			"abc123123!"
		);

		// when
		Account user = Account.from(signUpForm, new MockTokenGenerator("aaaa-aaaa-aaaa-aaaa"));

		// then
		assertThat(user.getId()).isNull();
		assertThat(user.getEmail()).isEqualTo("jyoung798@naver.com");
		assertThat(user.getNickname()).isEqualTo("jiny");
		assertThat(user.getPassword()).isEqualTo("abc123123!");
		assertThat(user.isValid()).isEqualTo(false);
		assertThat(user.getEmailAuthToken()).isEqualTo("aaaa-aaaa-aaaa-aaaa");
	}

	@Test
	public void 로그인시_마지막_로그인_시간이_기록된다() {
		// given
		Account account = Account.builder()
			.id(1L)
			.email("jyoung798@naver.com")
			.nickname("jiny")
			.password("abc123123!")
			.isValid(false)
			.lastLoginAt(100L)
			.emailAuthToken("aaaa-aaaa-aaaa-aaaa")
			.build();

		// when
		account.login(new MockClockHolder(167853L));

		// then
		assertThat(account.getLastLoginAt()).isEqualTo(167853L);
	}

	@Test
	public void 이메일_인증으로_계정을_활성화_할_수_있다() {
		// given
		Account account = Account.builder()
			.id(1L)
			.email("jyoung798@naver.com")
			.nickname("jiny")
			.password("abc123123!")
			.isValid(false)
			.lastLoginAt(100L)
			.emailAuthToken("aaaa-aaaa-aaaa-aaaa")
			.build();

		// when
		account.certificate("aaaa-aaaa-aaaa-aaaa");

		// then
		assertThat(account.isValid()).isEqualTo(true);
	}

	@Test
	public void 잘못된_인증_코드로_계정을_활성화_하려하면_에러를_던진다() {
		// given
		Account account = Account.builder()
			.id(1L)
			.email("jyoung798@naver.com")
			.nickname("jiny")
			.password("abc123123!")
			.isValid(false)
			.lastLoginAt(100L)
			.emailAuthToken("aaaa-aaaa-aaaa-aaaa")
			.build();

		// when & then
		assertThatThrownBy(() -> account.certificate("aaaa-aaaa-aaaa-aaa1"))
			.isInstanceOf(AuthTokenNotMatchedException.class);
	}
}
