package catchcompany.web.module.corporation.service.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.domain.InvestOfCorporation;

public interface InvestOfCorporationRepository {
	Page<InvestOfCorporation> findInvestByCompanyAndType(Corporation corporation, Pageable pageable, String type);

	Page<InvestOfCorporation> findInvestByCompany(Corporation corporation, Pageable pageable);

	InvestOfCorporation save(InvestOfCorporation invest);
}
