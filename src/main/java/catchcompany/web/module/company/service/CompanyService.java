package catchcompany.web.module.company.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import catchcompany.web.module.company.controller.dto.CompanyInfo;
import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.Invest;
import catchcompany.web.module.company.service.port.InvestRepository;
import catchcompany.web.module.company.service.port.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompanyService {

	private final CompanyRepository companyRepository;
	private final InvestRepository investRepository;

	public CompanyInfo findCompanyInvestInfo(String name,int pageNum, String type) {
		Pageable pageable = PageRequest.of(pageNum-1, 10);

		List<Company> companies = companyRepository.findByName(name);
		Company company = companies.get(0);
		Page<Invest> page;
		if(type.equals("상장")) {
			page = investRepository.findInvestInfoByCompanyAndType(company, pageable, type);
		}else{
			page = investRepository.findInvestInfoByCompany(company, pageable);
		}
		CompanyInfo companyInfo = new CompanyInfo(company, page);

		return companyInfo;
	}
}
