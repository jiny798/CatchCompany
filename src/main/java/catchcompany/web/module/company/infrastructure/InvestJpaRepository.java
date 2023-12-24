package catchcompany.web.module.company.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.Invest;

public interface InvestJpaRepository extends JpaRepository<Invest, Long> {

	@Query("select inv from Invest inv where inv.company = :company and inv.corporationClass = :type ")
	Page<Invest> findInvestByCompanyAndType(@Param("company") Company company, Pageable pageable, @Param("type") String type);

	@Query("select inv from Invest inv where inv.company = :company ")
	Page<Invest> findInvestByCompany(@Param("company") Company company, Pageable pageable);
}
