package catchcompany.web.module.stock.service;

import java.io.IOException;
import java.util.List;

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

	public void saveStockInfo() {
		RestTemplate restTemplate = new RestTemplate();
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
				"https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo")
			.queryParam("serviceKey", apiKey)
			.queryParam("numOfRows", 10000)
			.queryParam("pageNo", 1)
			.queryParam("resultType", "json")
			.queryParam("basDt", 20231214)
			.build(true);

		// ResponseEntity<StockInfoResult> result = restTemplate.getForEntity(uriComponents.toUri(), StockInfoResult.class);
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

		log.info("가져온 정보");
		for (int i = 0; i < list.size(); i++) {
			StockInfo stockInfo = list.get(i);
			boolean isIncrease = false;
			if (stockInfo.getMkp() < stockInfo.getClpr()) {
				isIncrease = true;
			}
			Stock stock = Stock.builder()
				.basDt(stockInfo.getBasDt())
				.itmsNm(stockInfo.getItmsNm())
				.mrktCtg(stockInfo.getMrktCtg())
				.clpr(stockInfo.getClpr())
				.fltRt(stockInfo.getFltRt())
				.vs(stockInfo.getVs())
				.mkp(stockInfo.getMkp())
				.mrktTotAmt(stockInfo.getMrktTotAmt())
				.trqu(stockInfo.getTrqu())
				.status(isIncrease)
				.build();
			stockRepository.save(stock);
		}



	}

}
