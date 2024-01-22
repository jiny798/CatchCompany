package catchcompany.web.module.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.company.service.processor.CompanyDataProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/company")
@RequiredArgsConstructor
public class AdminCompanyController {
	private final CompanyDataProcessor companyDataProcessor;

	@PostMapping("/insurance")
	public String processSaveCompanyInfo(@RequestBody String link) {
		log.info(link);
		companyDataProcessor.processSaveCompanyInfo(link);
		return "home";
	}


}
