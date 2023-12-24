package catchcompany.web.module.company.infrastructure;

import java.util.List;

import org.springframework.stereotype.Repository;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.service.port.CompanyRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {
	private final CompanyJpaRepository companyJpaRepository;
	@Override
	public List<Company> findByName(String name) {
		return companyJpaRepository.findByName(name);
	}

	@Override
	public Company findAllWithInvestInfo(String name) {
		return companyJpaRepository.findAllWithInvestInfo(name);
	}

	@Override
	public Company save(Company company) {
		return companyJpaRepository.save(company);
	}
}
