package catchcompany.web.module.pension.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.pension.controller.dto.InvestInfo;
import catchcompany.web.module.pension.controller.dto.QuarterInvestInfo;
import catchcompany.web.module.pension.service.PensionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/pension")
public class PensionController {

	private final PensionService pensionService;

	@GetMapping("/portfolio/{year}")
	public String pensionYearPortfolio(@PathVariable int year, Model model) {
		List<InvestInfo> list = pensionService.getInvestInfo(year);
		model.addAttribute("investInfoList", list);
		model.addAttribute("year", year);
		return "pension/pension_portfolio";
	}

	@GetMapping("/portfolio/quarter/{currentQuarter}")
	public String pensionQuarterPortfolio(@PathVariable String currentQuarter, Model model) {
		log.info("currentQuarter {}", currentQuarter);
		List<QuarterInvestInfo> quarterInvestInfoList = pensionService.getQuarterInvestInfo(currentQuarter);
		model.addAttribute("quarterInvestInfoList", quarterInvestInfoList);
		model.addAttribute("currentQuarter", currentQuarter);
		return "pension/pension_portfolio_quart";
	}

	@ModelAttribute("yearList")
	public List<Integer> yearList() {
		List<Integer> yearList = new ArrayList<>();
		yearList.add(2022);
		yearList.add(2021);
		yearList.add(2020);
		yearList.add(2019);
		return yearList;
	}

	@ModelAttribute("quarterList")
	public List<String> quarterList() {
		List<String> quarterList = new ArrayList<>();
		quarterList.add("2023-4분기");
		quarterList.add("2023-3분기");
		quarterList.add("2023-2분기");
		quarterList.add("2023-1분기");
		return quarterList;
	}
}
