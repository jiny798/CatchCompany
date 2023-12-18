package catchcompany.web.module.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.CompanyInvestInfo;

public interface CompanyInvestInfoRepository extends JpaRepository<CompanyInvestInfo, Long> {

	@Query("select cii from CompanyInvestInfo cii where cii.company = :company and cii.corporationClass = :type ")
	Page<CompanyInvestInfo> findInvestInfoByCompanyAndType(@Param("company") Company company, Pageable pageable, @Param("type") String type);

	@Query("select cii from CompanyInvestInfo cii where cii.company = :company ")
	Page<CompanyInvestInfo> findInvestInfoByCompany(@Param("company") Company company, Pageable pageable);
}
