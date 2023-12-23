package catchcompany.web.module.company.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.Invest;

public interface InvestJpaRepository extends JpaRepository<Invest, Long> {

	@Query("select cii from CompanyInvestInfo cii where cii.company = :company and cii.corporationClass = :type ")
	Page<Invest> findInvestInfoByCompanyAndType(@Param("company") Company company, Pageable pageable, @Param("type") String type);

	@Query("select cii from CompanyInvestInfo cii where cii.company = :company ")
	Page<Invest> findInvestInfoByCompany(@Param("company") Company company, Pageable pageable);
}
