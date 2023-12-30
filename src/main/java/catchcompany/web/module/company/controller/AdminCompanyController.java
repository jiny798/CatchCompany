package catchcompany.web.module.company.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catchcompany.web.module.company.service.admin.AdminCompanyService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/admin/company")
@RequiredArgsConstructor
@RestController
public class AdminCompanyController {

	private final AdminCompanyService adminCompanyService;

	@PostMapping()
	public void processCompanyInfoToDatabase() {
		adminCompanyService.processCompanyListToDatabase();
	}

	@PostMapping("/invest")
	public void processInvestInfoToDatabase() {
		adminCompanyService.processCompanyInvestInfoToDatabase();
	}

}
