package catchcompany.web.module.corporation.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.domain.InvestOfCorporation;
import catchcompany.web.module.corporation.repository.CorporationRepository;
import catchcompany.web.module.corporation.repository.InvestOfCorporationRepository;
import catchcompany.web.module.uri.application.UriManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCorporationService {
	private final UriManager uriManager;
	private final CorporationRepository corporationRepository;
	private final InvestOfCorporationRepository investRepository;
	private final CorporationDataProcessor corporationDataProcessor;
	private final CorporationInvestDataProcessor corporationInvestDataProcessor;

	public void processCompanyListToDatabase() {
		List<Corporation> corporationList = corporationDataProcessor.getCompanyList();
		corporationList.stream().parallel().forEach(i -> {
			corporationRepository.save(i);
		});
	}


	public void processCompanyInvestInfoToDatabase() {
		List<InvestOfCorporation> investList = corporationInvestDataProcessor.getInvestList();
		investList.stream().parallel()
			.forEach(invest -> {
				investRepository.save(invest);
			});
	}

}
