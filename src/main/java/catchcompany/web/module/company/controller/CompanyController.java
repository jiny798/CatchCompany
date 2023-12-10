package catchcompany.web.module.company.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catchcompany.web.module.company.controller.dto.CompanyInfo;
import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/company")
@RequiredArgsConstructor
@Controller
@Slf4j
public class CompanyController {

	private final CompanyService companyService;

	@GetMapping("/invest/{name}")
	public String findCompanyInvestInfo(Model model, @PathVariable String name) {
		log.info("검색한 회사명 : " + name);
		CompanyInfo companyInfo = companyService.findCompanyInvestInfo(name);
		model.addAttribute("name", companyInfo.getName());
		model.addAttribute("pageForInvest", companyInfo.getPageForInvest());
		model.addAttribute("pageForBusiness", companyInfo.getPageForBusiness());

		int blockLimit = 3;
		int startPage = 1;
		int endPageForInvest = companyInfo.getPageForInvest().getTotalPages();
		int endPageForBusiness = companyInfo.getPageForBusiness().getTotalPages();
		log.info("endPage {} , {} : ", endPageForInvest, endPageForBusiness);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPageForInvest", endPageForInvest);
		model.addAttribute("endPageForBusiness", endPageForBusiness);

		return "company/invest_info";
	}
}
