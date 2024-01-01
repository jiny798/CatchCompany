package catchcompany.web.module.corporation.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.domain.InvestOfCorporation;
import catchcompany.web.module.corporation.service.port.InvestOfCorporationRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InvestOfCorporationRepositoryImpl implements InvestOfCorporationRepository {
	private final InvestOfCorporationJpaRepository investOfCorporationJpaRepository;

	@Override
	public Page<InvestOfCorporation> findInvestByCompanyAndType(Corporation corporation, Pageable pageable, String type) {
		return investOfCorporationJpaRepository.findInvestByCompanyAndType(corporation, pageable, type);
	}

	@Override
	public Page<InvestOfCorporation> findInvestByCompany(Corporation corporation, Pageable pageable) {
		return investOfCorporationJpaRepository.findInvestByCompany(corporation, pageable);
	}

	@Override
	public InvestOfCorporation save(InvestOfCorporation invest) {
		return investOfCorporationJpaRepository.save(invest);
	}
}
