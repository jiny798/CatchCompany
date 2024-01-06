package catchcompany.web.module.corporation.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import catchcompany.web.module.corporation.controller.dto.CompanyInfo;
import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.domain.InvestOfCorporation;
import catchcompany.web.module.corporation.repository.CorporationRepository;
import catchcompany.web.module.corporation.repository.InvestOfCorporationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CorporationService {

	private final CorporationRepository corporationRepository;
	private final InvestOfCorporationRepository investRepository;

	public CompanyInfo findCompanyInvestInfo(String name,int pageNum, String type) {
		Pageable pageable = PageRequest.of(pageNum-1, 10);

		List<Corporation> companies = corporationRepository.findByName(name);
		Corporation corporation = companies.get(0);
		Page<InvestOfCorporation> page;
		if(type.equals("상장")) {
			page = investRepository.findInvestByCompanyAndType(corporation, pageable, type);
		}else{
			page = investRepository.findInvestByCompany(corporation, pageable);
		}
		CompanyInfo companyInfo = new CompanyInfo(corporation, page);

		return companyInfo;
	}
}
