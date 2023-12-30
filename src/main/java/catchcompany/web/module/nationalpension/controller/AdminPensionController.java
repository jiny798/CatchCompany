package catchcompany.web.module.nationalpension.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.nationalpension.controller.dto.InvestYearInfo;
import catchcompany.web.module.nationalpension.service.admin.PensionMonthStockDataProcessor;
import catchcompany.web.module.nationalpension.service.admin.PensionStockDataProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller @Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/pension")
public class AdminPensionController {
	private final PensionStockDataProcessor pensionStockDataProcessor;
	private final PensionMonthStockDataProcessor pensionMonthStockDataProcessor;

	@PostMapping("/invest")
	public String processInvestInfoSave(@ModelAttribute InvestYearInfo info) {
		log.info("info link - {}",info.getLink());
		pensionStockDataProcessor.saveInvestInfo(info);
		return "home";
	}

	@PostMapping("/sort/{year}")
	public String processInvestInfoSort(@PathVariable int year) {
		pensionStockDataProcessor.sortInvestInfo(year);
		return "home";
	}

	@PostMapping("/invest/month")
	public String processSaveMonthInvest() {
		pensionMonthStockDataProcessor.executeSaveMonthStock();
		return "home";
	}
}
