package catchcompany.web.module.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.CompanyInvestInfo;

public interface CompanyInvestInfoRepository extends JpaRepository<CompanyInvestInfo, Long> {

	@Query("select cii from CompanyInvestInfo cii where cii.investTarget = '투자' and cii.company = :company ")
	Page<CompanyInvestInfo> findByCompanyForInvest(@Param("company") Company company, Pageable pageable);

	@Query("select cii from CompanyInvestInfo cii where cii.investTarget != '투자' and cii.company = :company ")
	Page<CompanyInvestInfo> findByCompanyForBusiness(@Param("company")Company company, Pageable pageable);
}
