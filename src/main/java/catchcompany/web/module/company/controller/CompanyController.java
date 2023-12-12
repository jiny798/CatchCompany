package catchcompany.web.module.company.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catchcompany.web.module.company.controller.dto.CompanyInfo;
import catchcompany.web.module.company.controller.dto.SearchDto;
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

	@GetMapping("/invest")
	public String companyInvestInfoHome() {
		return "company/invest_info_search";
	}

	// GET /매일유업?page=0&size=5&sort=id,DESC
	@GetMapping("/invest/{name}/{page1}/{page2}")
	public String findCompanyInvestInfo(Model model, @PathVariable String name,
		@PathVariable int page1, @PathVariable int page2) {
		log.debug("검색한 회사명 : " + name);
		CompanyInfo companyInfo = companyService.findCompanyInvestInfo(name,page1,page2);
		model.addAttribute("name", companyInfo.getName());
		model.addAttribute("pageForInvest", companyInfo.getPageForInvest());
		model.addAttribute("pageForBusiness", companyInfo.getPageForBusiness());

		int pageLimit = 5;
		int startPage1 = (((page1 - 1) / pageLimit) * pageLimit) + 1 ;
		int startPage2 = (((page2 - 1) / pageLimit) * pageLimit) + 1 ;
		log.info("page start {} {}",startPage1, startPage2);
		int endPageForBusiness = Math.min((startPage1 + pageLimit - 1), companyInfo.getPageForBusiness().getTotalPages());
		int endPageForInvest = Math.min((startPage2 + pageLimit - 1), companyInfo.getPageForInvest().getTotalPages());
		model.addAttribute("startPage1", startPage1);
		model.addAttribute("startPage2", startPage2);
		model.addAttribute("endPageForInvest", endPageForInvest);
		model.addAttribute("endPageForBusiness", endPageForBusiness);
		model.addAttribute("currentPage1", page1);
		model.addAttribute("currentPage2", page2);

		return "company/invest_info";
	}
}
