package catchcompany.web.module.stock.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import catchcompany.web.module.date.DateManager;
import catchcompany.web.module.stock.controller.dto.FirstCondition;
import catchcompany.web.module.stock.controller.dto.StockSearch;
import catchcompany.web.module.stock.controller.dto.StockSearchResult;
import catchcompany.web.module.stock.controller.validation.StockSearchValidator;
import catchcompany.web.module.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/stock")
@RequiredArgsConstructor
@Controller
@Slf4j
public class StockController {
	private final StockService stockService;
	private final StockSearchValidator validator;

	@GetMapping
	public String getStockSearchPage(Model model) {
		model.addAttribute("stockSearch", new StockSearch("20231221", 20));
		return "stock/stock_search";
	}

	@PostMapping("/trading-volume")
	public String calculateStockInfo(Model model, StockSearch stockSearch, BindingResult bindingResult,
		RedirectAttributes redirectAttributes) {
		validator.validate(stockSearch, bindingResult);
		if (bindingResult.hasErrors()) {
			return "stock/stock_search";
		}
		List<StockSearchResult> searchResults = stockService.findStockByTradingVolume(stockSearch);
		redirectAttributes.addFlashAttribute("searchResults", searchResults);
		return "redirect:/stock";
	}

	@ModelAttribute("marketMap")
	public Map<String, String> marketMap() {
		Map<String, String> marketMap = new LinkedHashMap<>();
		marketMap.put("KOSPI", "코스피");
		marketMap.put("KOSDAQ", "코스닥");
		return marketMap;
	}

	@ModelAttribute("firstConditions")
	public FirstCondition[] firstConditions() {
		return FirstCondition.values();
	}
}
