package catchcompany.web.module.stock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import catchcompany.web.module.date.DateManager;
import catchcompany.web.module.stock.controller.dto.StockSearch;
import catchcompany.web.module.stock.controller.dto.StockSearchResult;
import catchcompany.web.module.stock.domain.Stock;
import catchcompany.web.module.stock.infra.repository.AdminStockJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {
	private final AdminStockJpaRepository adminStockJpaRepository;
	private final DateManager dateManager;

	/*
	 ** EX. {20231218}일에  전일 대비 거래량이 {10}배 이상 발생한 기업검색
	 * @Param date는 {20231218}, volume은 {10}배를 나타낸다
	 */
	public List<StockSearchResult> findStockByTradingVolume(StockSearch stockSearch) {
		String yesterdayDate = dateManager.calculatePreviousDateOf(stockSearch.getDate());
		log.info("조회된 어제 날짜 {}", yesterdayDate);
		List<StockSearchResult> answer = new ArrayList<>();
		List<Stock> today = adminStockJpaRepository.findByBasDt(stockSearch.getDate());
		List<Stock> yesterday = adminStockJpaRepository.findByBasDt(yesterdayDate);
		Map<String, Long> map = new HashMap<>();
		for (Stock stock : today) {
			map.put(stock.getSrtnCd(), stock.getTrqu());
		}
		for (Stock yesterdayStock : yesterday) {
			Long yesterdayTradingVolume = yesterdayStock.getTrqu();
			Long todayTradingVolume = map.get(yesterdayStock.getSrtnCd());
			if (todayTradingVolume == null || todayTradingVolume == 0 || yesterdayTradingVolume == 0)
				continue;
			if (yesterdayTradingVolume * stockSearch.getVolume() <= todayTradingVolume) {
				StockSearchResult stockSearchResult = new StockSearchResult(
					yesterdayStock.getItmsNm(),
					yesterdayTradingVolume,
					todayTradingVolume);
				answer.add(stockSearchResult);
			}
		}
		return answer;
	}

}
