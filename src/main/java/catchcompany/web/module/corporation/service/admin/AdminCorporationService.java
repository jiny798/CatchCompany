package catchcompany.web.module.corporation.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.domain.InvestOfCorporation;
import catchcompany.web.module.corporation.infra.repository.CorporationJdbcRepository;
import catchcompany.web.module.corporation.infra.repository.InvestOfCorporationJdbcRepository;
import catchcompany.web.module.corporation.service.admin.processor.CorporationDataProcessor;
import catchcompany.web.module.corporation.service.admin.processor.CorporationInvestDataProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCorporationService {
	private final CorporationJdbcRepository corporationJdbcRepository;
	private final InvestOfCorporationJdbcRepository investOfCorporationJdbcRepository;
	private final CorporationDataProcessor corporationDataProcessor;
	private final CorporationInvestDataProcessor corporationInvestDataProcessor;

	public void processCorporationListToDatabase() {
		List<Corporation> corporationList = corporationDataProcessor.getCompanyList();
		corporationJdbcRepository.saveAll(corporationList);
	}

	public void processCorporationInvestInfoToDatabase(MultipartFile multipartFile, int startRowNum, int endRowNum) {
		List<InvestOfCorporation> investList = corporationInvestDataProcessor.getInvestList(multipartFile, startRowNum,
			endRowNum);
		investOfCorporationJdbcRepository.saveAll(investList);
	}

}
