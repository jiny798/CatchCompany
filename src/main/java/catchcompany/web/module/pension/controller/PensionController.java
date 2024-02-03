package catchcompany.web.module.pension.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.common.infra.PageManager;
import catchcompany.web.module.pension.controller.dto.response.QuarterInvestInfo;
import catchcompany.web.module.pension.controller.dto.response.QuarterInvestResponse;
import catchcompany.web.module.pension.controller.dto.response.YearInvestResponse;
import catchcompany.web.module.pension.service.PensionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/pension")
public class PensionController {

	private final PensionService pensionService;
	private final PageManager pageManager;

	@GetMapping("/portfolio/{year}")
	public String pensionYearPortfolio(@PathVariable int year, Model model, Pageable pageable) {
		YearInvestResponse response = pensionService.findInvestInfoByYear(year, pageable);
		model.addAttribute("response", response);
		model.addAttribute("year", year);
		return "pension/pension_portfolio";
	}

	@GetMapping("/portfolio/quarter/{currentQuarter}")
	public String pensionQuarterPortfolio(@PathVariable String currentQuarter, Model model, Pageable pageable) {
		QuarterInvestResponse response = pensionService.findQuarterInvestInfoByQuarter(currentQuarter, pageable);
		model.addAttribute("response", response);
		model.addAttribute("quarter", currentQuarter);
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
