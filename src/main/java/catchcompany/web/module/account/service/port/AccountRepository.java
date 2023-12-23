package catchcompany.web.module.account.service.port;

import java.util.Optional;

import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.account.domain.entity.AccountStatus;

public interface AccountRepository {
	Optional<Account> findById(Long id);

	Optional<Account> findByIdAndStatus(Long id, AccountStatus accountStatus);

	Optional<Account> findByEmailAndStatus(String email, AccountStatus accountStatus);

	Account save(Account account);
}
