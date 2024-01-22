package catchcompany.web.module.stock.infra;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import catchcompany.web.module.stock.service.port.StockRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
public class StockRestClientImpl implements StockRestClient {

	private final RestTemplate restTemplate;

	@Value("${open-data.api-key}")
	private String apiKey;

	public String execute(int date) {
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
				"https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo")
			.queryParam("serviceKey", apiKey)
			.queryParam("numOfRows", 10000)
			.queryParam("pageNo", 1)
			.queryParam("resultType", "json")
			.queryParam("basDt", date)
			.build(true);
		return restTemplate.getForObject(uriComponents.toUri(), String.class);
	}
}
