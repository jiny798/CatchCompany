package catchcompany.web.module.account.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.account.domain.entity.AccountStatus;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

	public Optional<Account> findByIdAndStatus(Long id, AccountStatus accountStatus);

	public Optional<Account> findByEmailAndStatus(String email, AccountStatus accountStatus);
}
