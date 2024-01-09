package catchcompany.web.module.account.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import catchcompany.web.module.account.domain.UserAccount;
import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.account.service.port.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {

	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String nickname) {
		Account account = accountRepository.findByNickname(nickname).orElseThrow(() -> {
			return new UsernameNotFoundException("로그인에 실패하였습니다.");
		});

		return new UserAccount(account);
	}
}
