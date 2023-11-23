package catchcompany.web.module.company.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catchcompany.web.module.company.application.FinancialSupervisoryService;
import catchcompany.web.module.company.application.InvestInfoService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/financial-supervisory")
@RequiredArgsConstructor
@RestController
public class FinancialSupervisoryController {
	private final FinancialSupervisoryService financialSupervisoryService;
	private final InvestInfoService investInfoService;

	@PostMapping("/company")
	public void processCompanyInfoToDatabase(){
		financialSupervisoryService.processCompanyInfoToDatabase();
	}

}
