package catchcompany.web.module.company.service.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.Invest;

public interface InvestRepository {
	Page<Invest> findInvestInfoByCompanyAndType(Company company, Pageable pageable, String type);

	Page<Invest> findInvestInfoByCompany(Company company, Pageable pageable);

	Invest save(Invest invest);
}
