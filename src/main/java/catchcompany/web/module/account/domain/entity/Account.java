package catchcompany.web.module.account.domain.entity;


import catchcompany.web.module.account.controller.dto.SignUpForm;
import catchcompany.web.module.account.exception.AuthTokenNotMatchedException;
import catchcompany.web.module.common.service.port.ClockHolder;
import catchcompany.web.module.common.service.port.TokenGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ToString
public class Account {

	@Id
	@GeneratedValue
	@Column(name = "account_id")
	private Long id;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String nickname;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String emailAuthToken;

	@Column(nullable = false)
	private boolean isValid;
	private long lastLoginAt;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Role role;

	public static Account from(SignUpForm form, TokenGenerator tokenGenerator) {
		return Account.builder()
			.email(form.getEmail())
			.nickname(form.getNickname())
			.password(form.getPassword())
			.isValid(false)
			.emailAuthToken(tokenGenerator.random())
			.role(Role.ROLE_USER)
			.build();
	}

	public void login(ClockHolder clockHolder) {
		this.lastLoginAt = clockHolder.millis();
	}

	public void certificate(String emailAuthToken) {
		if (!this.emailAuthToken.equals(emailAuthToken)) {
			throw new AuthTokenNotMatchedException();
		}
		this.isValid = true;
	}
}
