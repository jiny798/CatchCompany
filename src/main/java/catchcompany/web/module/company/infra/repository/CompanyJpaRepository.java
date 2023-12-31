package catchcompany.web.module.company.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.company.domain.Company;

public interface CompanyJpaRepository extends JpaRepository<Company, Long> {

	Optional<Company> findByRegistrationCodeAndName(String registrationCode, String name);

	default Company getByRegistrationCodeAndName(String registrationCode, String name) {
		return this.findByRegistrationCodeAndName(registrationCode,name).orElse(null);
	}
}
