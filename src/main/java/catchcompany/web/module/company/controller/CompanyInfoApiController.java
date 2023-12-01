package catchcompany.web.module.company.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catchcompany.web.module.company.service.CompanyInfoApiService;
import catchcompany.web.module.company.service.CompanyInvestInfoService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/financial-supervisory")
@RequiredArgsConstructor
@RestController
public class CompanyInfoApiController {

	private final CompanyInfoApiService companyInfoApiService;
	private final CompanyInvestInfoService companyInvestInfoService;

	@PostMapping("/company")
	public void processCompanyInfoToDatabase() {
		companyInfoApiService.processCompanyInfoToDatabase();
	}

	@PostMapping("/comapny/invest-info")
	public void processInvestInfoToDatabase() {
		companyInvestInfoService.processCompanyInvestInfoToDatabase();
	}

}
