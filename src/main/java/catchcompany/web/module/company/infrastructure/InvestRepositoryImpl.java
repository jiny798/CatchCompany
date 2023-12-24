package catchcompany.web.module.company.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.Invest;
import catchcompany.web.module.company.service.port.InvestRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InvestRepositoryImpl implements InvestRepository {
	private final InvestJpaRepository investJpaRepository;

	@Override
	public Page<Invest> findInvestByCompanyAndType(Company company, Pageable pageable, String type) {
		return investJpaRepository.findInvestByCompanyAndType(company, pageable, type);
	}

	@Override
	public Page<Invest> findInvestByCompany(Company company, Pageable pageable) {
		return investJpaRepository.findInvestByCompany(company, pageable);
	}

	@Override
	public Invest save(Invest invest) {
		return investJpaRepository.save(invest);
	}
}
