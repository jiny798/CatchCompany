package catchcompany.web.module.company.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catchcompany.web.module.company.service.AdminCompanyService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/financial-supervisory")
@RequiredArgsConstructor
@RestController
public class AdminCompanyController {

	private final AdminCompanyService adminCompanyService;

	@PostMapping("/company")
	public void processCompanyInfoToDatabase() {
		adminCompanyService.processCompanyListToDatabase();
	}

	@PostMapping("/comapny/invest-info")
	public void processInvestInfoToDatabase() {
		adminCompanyService.processCompanyInvestInfoToDatabase();
	}

}
