package catchcompany.web.module.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.company.domain.CompanyInvestInfo;

public interface CompanyInvestInfoRepository extends JpaRepository<CompanyInvestInfo, Long> {
}
