package catchcompany.web.module.account.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.account.domain.entity.AccountStatus;
import catchcompany.web.module.account.service.port.AccountRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
	private final AccountJpaRepository accountJpaRepository;

	@Override
	public Optional<Account> findById(Long id) {
		return accountJpaRepository.findById(id);
	}

	@Override
	public Optional<Account> findByIdAndStatus(Long id, AccountStatus accountStatus) {
		return accountJpaRepository.findByIdAndStatus(id, accountStatus);
	}

	@Override
	public Optional<Account> findByEmailAndStatus(String email, AccountStatus accountStatus) {
		return accountJpaRepository.findByEmailAndStatus(email, accountStatus);
	}

	@Override
	public Account save(Account account) {
		return accountJpaRepository.save(account);
	}
}
