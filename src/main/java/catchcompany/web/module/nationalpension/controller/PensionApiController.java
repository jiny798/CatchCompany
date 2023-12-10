package catchcompany.web.module.nationalpension.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.nationalpension.controller.dto.InvestYearInfo;
import catchcompany.web.module.nationalpension.service.PensionApiService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/national-pension")
public class PensionApiController {
	private final PensionApiService pensionApiService;

	@PostMapping("/invest")
	public String processInvestInfoSave(@ModelAttribute InvestYearInfo info) {
		pensionApiService.processInvestInfoSave(info);
		return "home";
	}
}
