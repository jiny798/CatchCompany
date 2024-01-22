package catchcompany.web.module.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catchcompany.web.module.stock.service.AdminStockService;
import catchcompany.web.module.stock.service.processor.StockInfoProcessor;
import lombok.RequiredArgsConstructor;

@RequestMapping("/admin/stock")
@RequiredArgsConstructor
@RestController
public class AdminStockController {

	private final AdminStockService adminStockService;

	@PostMapping("/{date}")
	public String saveStockInfo(@PathVariable int date) {
		adminStockService.processSaveDailyStockInfo(date);
		return "ok";
	}
}
