package catchcompany.web.module.corporation.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.domain.InvestOfCorporation;
import catchcompany.web.module.corporation.infra.repository.CorporationJdbcRepository;
import catchcompany.web.module.corporation.infra.repository.CorporationJpaRepository;
import catchcompany.web.module.corporation.infra.repository.InvestOfCorporationJpaRepository;
import catchcompany.web.module.uri.application.UriManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCorporationService {
	private final CorporationJdbcRepository corporationJdbcRepository;
	private final InvestOfCorporationJpaRepository investRepository;
	private final CorporationDataProcessor corporationDataProcessor;
	private final CorporationInvestDataProcessor corporationInvestDataProcessor;

	public void processCorporationListToDatabase() {
		List<Corporation> corporationList = corporationDataProcessor.getCompanyList();
		corporationJdbcRepository.saveAll(corporationList);
	}

	public void processCorporationInvestInfoToDatabase() {
		List<InvestOfCorporation> investList = corporationInvestDataProcessor.getInvestList();
		investList.stream().parallel()
			.forEach(invest -> {
				investRepository.save(invest);
			});
	}

}
