package catchcompany.web.module.nationalpension.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.nationalpension.controller.dto.InvestYearInfo;
import catchcompany.web.module.nationalpension.service.PensionApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller @Slf4j
@RequiredArgsConstructor
@RequestMapping("/national-pension")
public class PensionApiController {
	private final PensionApiClient pensionApiClient;

	@PostMapping("/invest")
	public String processInvestInfoSave(@ModelAttribute InvestYearInfo info) {
		log.info("info link - {}",info.getLink());
		pensionApiClient.saveInvestInfo(info);
		return "home";
	}

	@PostMapping("/sort/{year}")
	public String processInvestInfoSort(@PathVariable int year) {
		pensionApiClient.sortInvestInfo(year);
		return "home";
	}
}
