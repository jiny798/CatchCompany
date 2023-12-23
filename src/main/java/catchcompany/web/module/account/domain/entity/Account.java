package catchcompany.web.module.account.domain.entity;

import java.time.Clock;

import catchcompany.web.module.account.exception.AuthTokenNotMatchedException;
import catchcompany.web.module.common.service.port.ClockHolder;
import catchcompany.web.module.common.service.port.TokenGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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

	private String password;

	private String emailAuthToken;

	private boolean isValid;
	private long lastLoginAt;

	public static Account from(AccountCreate accountCreate, TokenGenerator tokenGenerator) {
		return Account.builder()
			.email(accountCreate.getEmail())
			.nickname(accountCreate.getNickname())
			.password(accountCreate.getPassword())
			.isValid(false)
			.emailAuthToken(tokenGenerator.random())
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
