package catchcompany.web.module.stock.service.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import catchcompany.web.module.stock.domain.Stock;
import catchcompany.web.module.stock.exception.AlreadyProcessedStockDateException;
import catchcompany.web.module.stock.infra.repository.StockJpaRepository;
import catchcompany.web.module.stock.service.dto.StockInfo;
import catchcompany.web.module.stock.service.port.StockRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class StockInfoProcessor {
	private final StockJpaRepository stockJpaRepository;
	private final StockRestClient stockRestClient;

	public List<Stock> getStockList(int date) {
		if (!stockJpaRepository.findByBasDt(String.valueOf(date)).isEmpty()) {
			throw new AlreadyProcessedStockDateException("이미 처리된 일자 입니다.");
		}
		String result = stockRestClient.execute(date);
		ObjectMapper mapper = new ObjectMapper();
		List<StockInfo> list = null;

		try {
			JsonNode root = mapper.readTree(result);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			JsonNode listNode = root.path("response").path("body").path("items").path("item");
			list = mapper.convertValue(listNode,
				TypeFactory.defaultInstance().constructCollectionType(List.class, StockInfo.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.debug("stock info List size = {}", list.size());
		List<Stock> stockList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			StockInfo stockInfo = list.get(i);
			boolean isIncrease = false;
			if (stockInfo.getMkp() < stockInfo.getClpr()) {
				isIncrease = true;
			}
			Stock stock = Stock.builder()
				.basDt(stockInfo.getBasDt())
				.srtnCd(stockInfo.getSrtnCd())
				.isinCd(stockInfo.getIsinCd())
				.itmsNm(stockInfo.getItmsNm())
				.mrktCtg(stockInfo.getMrktCtg())
				.clpr(stockInfo.getClpr())
				.vs(stockInfo.getVs())
				.fltRt(stockInfo.getFltRt())
				.mkp(stockInfo.getMkp())
				.hipr(stockInfo.getHipr())
				.lopr(stockInfo.getLopr())
				.trqu(stockInfo.getTrqu())
				.trPrc(stockInfo.getTrPrc())
				.lstgStCnt(stockInfo.getLstgStCnt())
				.mrktTotAmt(stockInfo.getMrktTotAmt())
				.status(isIncrease)
				.build();
			stockList.add(stock);
		}
		return stockList;
	}

}
