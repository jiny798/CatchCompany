package catchcompany.web.module.pension.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import catchcompany.web.module.pension.controller.dto.InvestYearInfo;
import catchcompany.web.module.pension.infrastructure.dto.PensionStockRestResponse;
import catchcompany.web.module.pension.service.port.PensionStockRestClient;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PensionStockRestClientImpl implements PensionStockRestClient {

	private final RestTemplate restTemplate;

	@Value("${open-data.api-key}")
	private String apiKey;

	@Override
	public PensionStockRestResponse execute(InvestYearInfo investYearInfo) {
		UriComponents uriComponents = UriComponentsBuilder
			.fromHttpUrl(investYearInfo.getLink())
			.queryParam("serviceKey", apiKey)
			.queryParam("perPage", 1500)
			.build(true);
		ResponseEntity<PensionStockRestResponse> result = restTemplate.getForEntity(uriComponents.toUri(),
			PensionStockRestResponse.class);
		return result.getBody();
	}
}
