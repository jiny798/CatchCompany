package catchcompany.web.module.corporation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catchcompany.web.module.corporation.service.admin.AdminCorporationService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/admin/corporation")
@RequiredArgsConstructor
@RestController
public class AdminCorporationController {

	private final AdminCorporationService adminCorporationService;

	@PostMapping()
	public void processCompanyInfoToDatabase() {
		adminCorporationService.processCompanyListToDatabase();
	}

	@PostMapping("/invest")
	public void processInvestInfoToDatabase() {
		adminCorporationService.processCompanyInvestInfoToDatabase();
	}

}
