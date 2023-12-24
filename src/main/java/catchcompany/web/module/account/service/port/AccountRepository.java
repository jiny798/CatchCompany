package catchcompany.web.module.account.service.port;

import java.util.Optional;

import catchcompany.web.module.account.domain.entity.Account;

public interface AccountRepository {
	Optional<Account> findById(Long id);

	Optional<Account> findByIdAndIsValid(Long id, boolean isValid);

	Optional<Account> findByEmailAndIsValid(String email, boolean isValid);

	Account save(Account account);
}
