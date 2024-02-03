package catchcompany.web.module.corporation.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import catchcompany.web.module.common.dto.PageInfo;
import catchcompany.web.module.common.infra.PageManager;
import catchcompany.web.module.corporation.controller.dto.response.CorporationInvestInfo;
import catchcompany.web.module.corporation.controller.dto.response.InvestInfoResponse;
import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.domain.InvestOfCorporation;
import catchcompany.web.module.corporation.infra.repository.CorporationJpaRepository;
import catchcompany.web.module.corporation.infra.repository.InvestOfCorporationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CorporationService {

	private final CorporationJpaRepository corporationRepository;
	private final InvestOfCorporationJpaRepository investRepository;
	private final PageManager pageManager;

	public InvestInfoResponse findCompanyInvestInfo(String name, int pageNum, String type) {
		Pageable pageable = PageRequest.of(pageNum - 1, 10);
		List<Corporation> companies = corporationRepository.findByName(name);
		Corporation corporation = companies.get(0);
		Page<InvestOfCorporation> investOfCorporationPage;
		if (type.equals("상장")) {
			// todo: 동적 쿼리로 변경 필요
			investOfCorporationPage = investRepository.findInvestByCompanyAndType(corporation, pageable, type);
		} else {
			investOfCorporationPage = investRepository.findInvestByCompany(corporation, pageable);
		}
		PageInfo pageInfo = pageManager.generatePageInfo(investOfCorporationPage, pageNum, 10);
		Page<CorporationInvestInfo> yearInvestInfoPage = investOfCorporationPage.map(investOfCorporation ->
			new CorporationInvestInfo(investOfCorporation)
		);

		InvestInfoResponse investInfoResponse = new InvestInfoResponse(name, type, yearInvestInfoPage, pageInfo);
		return investInfoResponse;
	}
}
