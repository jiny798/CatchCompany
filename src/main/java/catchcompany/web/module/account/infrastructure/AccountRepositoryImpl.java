package catchcompany.web.module.account.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import catchcompany.web.module.account.domain.entity.Account;
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
	public Optional<Account> findByIdAndIsValid(Long id, boolean isValid) {
		return accountJpaRepository.findByIdAndIsValid(id, isValid);
	}

	@Override
	public Optional<Account> findByEmailAndIsValid(String email, boolean isValid) {
		return accountJpaRepository.findByEmailAndIsValid(email, isValid);
	}

	@Override
	public Account save(Account account) {
		return accountJpaRepository.save(account);
	}
}
