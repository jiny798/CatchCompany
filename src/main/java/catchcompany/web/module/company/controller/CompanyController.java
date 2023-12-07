package catchcompany.web.module.company.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catchcompany.web.module.company.controller.dto.CompanyInfo;
import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/company")
@RequiredArgsConstructor
@RestController @Slf4j
public class CompanyController {

	private final CompanyService companyService;

	@GetMapping("/invest/{name}")
	public CompanyInfo findCompanyInvestInfo(@PathVariable String name) {
		log.info("검색한 회사명 : "+name);
		CompanyInfo companyInfo = companyService.findCompanyInvestInfo(name);

		return companyInfo;
	}
}
