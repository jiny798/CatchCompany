package catchcompany.web.module.nationalpension.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.nationalpension.service.PensionApiService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/national-pension")
public class PensionApiController {
	private final PensionApiService pensionApiService;

	@PostMapping("/invest")
	public String processInvestInfoSave() {
		pensionApiService.processInvestInfoSave();
		return "home";
	}
}
