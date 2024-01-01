package catchcompany.web.module.corporation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.corporation.controller.dto.CompanyInfo;
import catchcompany.web.module.corporation.service.CorporationService;
import catchcompany.web.module.corporation.service.port.CorporationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/company")
@RequiredArgsConstructor
@Controller
@Slf4j
public class CorporationController {

	private final CorporationService corporationService;
	private final CorporationRepository corporationRepository;

	@GetMapping("/invest")
	public String companyInvestInfoHome() {
		return "company/invest_info_search";
	}

	// GET /매일유업?page=0&size=5&sort=id,DESC
	@GetMapping("/invest/{name}/{page}/{type}")
	public String findCompanyInvestInfo(Model model, @PathVariable String name,
		@PathVariable int page, @PathVariable String type) {
		log.info(name + " -------------");
		if (name == null || name.isBlank() || !corporationRepository.existsByName(name)) {
			model.addAttribute("errorName", name);
			return "company/invest_info_search";
		}

		CompanyInfo companyInfo = corporationService.findCompanyInvestInfo(name, page, type);
		model.addAttribute("name", companyInfo.getName());
		model.addAttribute("page", companyInfo.getPage());

		int pageLimit = 10;
		int startPage = (((page - 1) / pageLimit) * pageLimit) + 1;
		int endPage = Math.min((startPage + pageLimit - 1), companyInfo.getPage().getTotalPages());
		if(endPage == 0) endPage = 1;

		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", page);
		model.addAttribute("currentType", type);

		return "company/invest_info";
	}

	@ModelAttribute("typeList")
	public List<String> typeList() {
		List<String> typeList = new ArrayList<>();
		typeList.add("상장");
		typeList.add("전체");
		return typeList;
	}
}
