package catchcompany.web.module.pension.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import catchcompany.web.module.pension.controller.dto.RequestYearInfo;
import catchcompany.web.module.pension.service.AdminPensionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/pension")
public class AdminPensionController {
	private final AdminPensionService adminPensionService;

	@PostMapping("/invest")
	public String processInvestInfoSave(@RequestBody List<RequestYearInfo> list) {
		adminPensionService.processInvestInfoSave(list);
		return "ok";
	}

	@PostMapping("/invest/quarter")
	public String processSaveQuarterInvest(@RequestParam MultipartFile file, @RequestParam String quarter,
		@RequestParam int beforeYear) {
		adminPensionService.processSaveQuarterInvest(file, quarter, beforeYear);
		return "ok";
	}

	@GetMapping("/invest/quarter/name-check")
	public List<String> checkNotExistName(@RequestParam MultipartFile file, @RequestParam int beforeYear) {
		List<String> notExistNameList = adminPensionService.checkExistCorporationName(file, beforeYear);
		return notExistNameList;
	}
}
