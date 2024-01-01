package catchcompany.web.module.pension.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.pension.controller.dto.InvestYearInfo;
import catchcompany.web.module.pension.service.PensionMonthStockDataProcessor;
import catchcompany.web.module.pension.service.PensionYearStockDataProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller @Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/pension")
public class AdminPensionController {
	private final PensionYearStockDataProcessor pensionYearStockDataProcessor;
	private final PensionMonthStockDataProcessor pensionMonthStockDataProcessor;

	@PostMapping("/invest")
	public String processInvestInfoSave(@ModelAttribute InvestYearInfo info) {
		log.info("info link - {}",info.getLink());
		pensionYearStockDataProcessor.saveInvestInfo(info);
		return "home";
	}

	@PostMapping("/sort/{year}")
	public String processInvestInfoSort(@PathVariable int year) {
		pensionYearStockDataProcessor.sortInvestInfo(year);
		return "home";
	}

	@PostMapping("/invest/month")
	public String processSaveMonthInvest() {
		pensionMonthStockDataProcessor.executeSaveMonthStock();
		return "home";
	}
}
