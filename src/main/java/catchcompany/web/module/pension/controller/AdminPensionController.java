package catchcompany.web.module.pension.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.pension.controller.dto.InvestYearInfo;
import catchcompany.web.module.pension.service.admin.AdminPensionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/pension")
public class AdminPensionController {
	private final AdminPensionService adminPensionService;

	@PostMapping("/invest")
	public String processInvestInfoSave(@ModelAttribute InvestYearInfo investYearInfo) {
		adminPensionService.processInvestInfoSave(investYearInfo);
		return "home";
	}

	@PostMapping("/sort/{year}")
	public String processInvestInfoSort(@PathVariable int year) {
		adminPensionService.processInvestInfoSort(year);
		return "home";
	}

	@PostMapping("/invest/month")
	public String processSaveMonthInvest() {
		adminPensionService.processSaveMonthInvest();
		return "home";
	}
}
