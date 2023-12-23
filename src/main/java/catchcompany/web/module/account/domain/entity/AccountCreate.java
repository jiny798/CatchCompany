package catchcompany.web.module.account.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountCreate {
	private final String email;
	private final String nickname;
	private final String password;

	@Builder
	public AccountCreate(String email, String nickname, String password) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}
}
