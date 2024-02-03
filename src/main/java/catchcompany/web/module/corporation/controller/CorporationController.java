package catchcompany.web.module.corporation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.common.infra.PageManager;
import catchcompany.web.module.corporation.controller.dto.response.InvestInfoResponse;
import catchcompany.web.module.corporation.infra.repository.CorporationJpaRepository;
import catchcompany.web.module.corporation.service.CorporationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/company")
@RequiredArgsConstructor
@Controller
@Slf4j
public class CorporationController {

	private final CorporationService corporationService;
	private final CorporationJpaRepository corporationRepository;
	private final PageManager pageManager;

	@GetMapping("/invest")
	public String companyInvestInfoHome() {
		return "company/invest_info_search";
	}

	// GET /매일유업?page=0&size=5&sort=id,DESC
	@GetMapping("/invest/{name}/{pageNum}/{type}")
	public String findCompanyInvestInfo(Model model, @PathVariable String name,
		@PathVariable int pageNum, @PathVariable String type) {

		if (name == null || name.isBlank() || !corporationRepository.existsByName(name)) {
			model.addAttribute("errorName", name);
			return "company/invest_info_search";
		}
		InvestInfoResponse investInfoResponse = corporationService.findCompanyInvestInfo(name, pageNum, type);
		model.addAttribute("response", investInfoResponse);

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
