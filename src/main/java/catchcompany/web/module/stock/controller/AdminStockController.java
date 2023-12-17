package catchcompany.web.module.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.stock.service.StockInfoClient;
import lombok.RequiredArgsConstructor;

@RequestMapping("/admin/stock")
@RequiredArgsConstructor
@Controller
public class AdminStockController {

	private final StockInfoClient stockInfoClient;

	@PostMapping
	public String saveStockInfo() {
		stockInfoClient.saveStockInfo();
		return "home";
	}
}
