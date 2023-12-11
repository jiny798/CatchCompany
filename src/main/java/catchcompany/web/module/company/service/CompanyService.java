package catchcompany.web.module.company.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import catchcompany.web.module.company.controller.dto.CompanyInfo;
import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.CompanyInvestInfo;
import catchcompany.web.module.company.repository.CompanyInvestInfoRepository;
import catchcompany.web.module.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompanyService {

	private final CompanyRepository companyRepository;
	private final CompanyInvestInfoRepository companyInvestInfoRepository;

	public CompanyInfo findCompanyInvestInfo(String name,int page1, int page2) {
		Pageable pageableForBusiness = PageRequest.of(page1-1, 5);
		Pageable pageableForInvest = PageRequest.of(page2-1, 5);

		List<Company> companies = companyRepository.findByName(name);
		Company company = companies.get(0);
		Page<CompanyInvestInfo> pageForInvest = companyInvestInfoRepository.findByCompanyForInvest(company, pageableForInvest);
		Page<CompanyInvestInfo> pageForBusiness = companyInvestInfoRepository.findByCompanyForBusiness(company, pageableForBusiness);

		CompanyInfo companyInfo = new CompanyInfo(company, pageForInvest, pageForBusiness);

		return companyInfo;
	}
}
