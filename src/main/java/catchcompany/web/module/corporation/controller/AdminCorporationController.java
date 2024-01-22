package catchcompany.web.module.corporation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import catchcompany.web.module.corporation.service.admin.AdminCorporationService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/admin/corporation")
@RequiredArgsConstructor
@RestController
public class AdminCorporationController {

	private final AdminCorporationService adminCorporationService;

	@PostMapping()
	public void processCompanyInfoToDatabase() {
		adminCorporationService.processCorporationListToDatabase();
	}

	@PostMapping("/invest")
	public void processInvestInfoToDatabase(@RequestParam MultipartFile file, @RequestParam int startRowNum,
		@RequestParam int endRowNum) {
		adminCorporationService.processCorporationInvestInfoToDatabase(file, startRowNum, endRowNum);
	}

}
