package catchcompany.web.module.company.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.company.domain.Company;

public interface CompanyJpaRepository extends JpaRepository<Company, Long> {

	List<Company> findByName(String name);

	@Query("select c from Company c where c.name = :name")
	Company findAllWithInvestInfo(@Param("name") String name);
}
