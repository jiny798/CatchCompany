package catchcompany.web.module.company.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catchcompany.web.module.company.service.CompanyInfoClient;
import lombok.RequiredArgsConstructor;

@RequestMapping("/financial-supervisory")
@RequiredArgsConstructor
@RestController
public class AdminCompanyController {

	private final CompanyInfoClient companyInfoClient;

	@PostMapping("/company")
	public void processCompanyInfoToDatabase() {
		companyInfoClient.processCompanyInfoToDatabase();
	}

	@PostMapping("/comapny/invest-info")
	public void processInvestInfoToDatabase() {
		companyInfoClient.processCompanyInvestInfoToDatabase();
	}

}
