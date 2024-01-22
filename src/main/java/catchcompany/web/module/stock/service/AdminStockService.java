package catchcompany.web.module.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import catchcompany.web.module.stock.domain.Stock;
import catchcompany.web.module.stock.infra.repository.StockJdbcRepository;
import catchcompany.web.module.stock.service.processor.StockInfoProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStockService {
	private final StockInfoProcessor stockInfoProcessor;
	private final StockJdbcRepository stockJdbcRepository;

	public void processSaveDailyStockInfo(int date) {
		List<Stock> stockList = stockInfoProcessor.getStockList(date);
		log.debug("stock list = {}", stockList.size());
		stockJdbcRepository.saveAll(stockList);
	}
}
