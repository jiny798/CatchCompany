package catchcompany.web.module.account.application;

import org.springframework.stereotype.Service;

import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.common.service.port.ClockHolder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final ClockHolder clockHolder;

	public void login(Account account) {
		account.login(clockHolder);
	}
}
