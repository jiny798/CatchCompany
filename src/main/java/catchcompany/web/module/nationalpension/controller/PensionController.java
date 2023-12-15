package catchcompany.web.module.nationalpension.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.nationalpension.controller.dto.InvestYearInfo;
import catchcompany.web.module.nationalpension.service.PensionService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pension")
public class PensionController {

	private final PensionService pensionService;

	@GetMapping("/portfolio")
	public String processInvestInfoSave() {

		return "pension/pension_portfolio";
	}

	@ModelAttribute("yearList")
	public List<Integer> yearList(){
		List<Integer> yearList = new ArrayList<>();
		yearList.add(2022);
		yearList.add(2021);
		yearList.add(2020);
		yearList.add(2019);
		yearList.add(2018);
		return yearList;
	}
}
