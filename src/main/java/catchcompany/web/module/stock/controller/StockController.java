package catchcompany.web.module.stock.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.stock.domain.Item;
import catchcompany.web.module.stock.domain.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/stock")
@RequiredArgsConstructor
@Controller
@Slf4j
public class StockController {

	@GetMapping
	public String getStockSearchPage(Model model) {
		Item item = new Item(1L,"abc",1,1);
		model.addAttribute("item",item);
		return "stock/stock_search";
	}

	@ModelAttribute("regions")
	public Map<String, String> regions() {
		Map<String, String> regions = new LinkedHashMap<>();
		regions.put("SEOUL", "코스피");
		regions.put("BUSAN", "코스닥");
		return regions;
	}

	@ModelAttribute("itemTypes")
	public ItemType[] itemTypes() {
		return ItemType.values();
	}
}
