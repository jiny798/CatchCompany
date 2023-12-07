package catchcompany.web.module.company.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import catchcompany.web.module.company.controller.dto.CompanyInfo;
import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.CompanyInvestInfo;
import catchcompany.web.module.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
@RequiredArgsConstructor
@Transactional
public class CompanyService {

	private final CompanyRepository companyRepository;

	public CompanyInfo findCompanyInvestInfo(String name) {
		Company company = companyRepository.findAllWithInvestInfo(name);
		log.info("강제초기화 부분");
		CompanyInfo companyInfo = new CompanyInfo(company);

		return companyInfo;
	}
}
