package catchcompany.web.module.stock.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import catchcompany.web.module.stock.domain.Stock;
import catchcompany.web.module.stock.exception.AlreadyProcessedStockDateException;
import catchcompany.web.module.stock.repository.StockRepository;
import catchcompany.web.module.stock.service.dto.StockInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class StockInfoClient {
	@Value("${open-data.api-key}")
	private String apiKey;
	private final StockRepository stockRepository;

	public void saveStockInfo(int date) {
		if (!stockRepository.findByBasDt(String.valueOf(date)).isEmpty()) {
			throw new AlreadyProcessedStockDateException("이미 처리된 일자 입니다.");
		}

		RestTemplate restTemplate = new RestTemplate();
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
				"https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo")
			.queryParam("serviceKey", apiKey)
			.queryParam("numOfRows", 10000)
			.queryParam("pageNo", 1)
			.queryParam("resultType", "json")
			.queryParam("basDt", date)
			.build(true);

		String result = restTemplate.getForObject(uriComponents.toUri(), String.class);
		ObjectMapper mapper = new ObjectMapper();
		String city = "";
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
			stockRepository.save(stock);
		}

	}

	public void saveAllDateStockInfo() {
		RestTemplate restTemplate = new RestTemplate();

		IntStream intStream = IntStream.range(1, 257); // 현재 256 이 마지막 페이지

		intStream.parallel().forEach(cnt -> {
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
					"https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo")
				.queryParam("serviceKey", apiKey)
				.queryParam("numOfRows", 10000)
				.queryParam("pageNo", cnt)
				.queryParam("resultType", "json")
				.build(true);
			String result = restTemplate.getForObject(uriComponents.toUri(), String.class);

			ObjectMapper mapper = new ObjectMapper();
			String city = "";
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

			IntStream stream = IntStream.range(0, list.size());
			List<StockInfo> finalList = list;
			stream.parallel().forEach(index -> {
				StockInfo stockInfo = finalList.get(index);
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
				stockRepository.save(stock);
			});
		});

	}
}
