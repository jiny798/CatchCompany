package catchcompany.web.module.account.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.account.domain.entity.Account;

@Repository
public interface AccountJpaRepository extends JpaRepository<Account, Long> {

	public Optional<Account> findByIdAndIsValid(Long id, boolean isValid);

	public Optional<Account> findByEmailAndIsValid(String email, boolean isValid);

	Boolean existsByEmail(String email);

	Boolean existsByNickname(String nickname);
}