package catchcompany.web.module.pension.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import catchcompany.web.module.pension.controller.dto.RequestYearInfo;
import catchcompany.web.module.pension.infra.dto.PensionStockRestResponse;
import catchcompany.web.module.pension.service.port.PensionStockRestClient;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PensionStockRestClientImpl implements PensionStockRestClient {

	private final RestTemplate restTemplate;

	@Value("${open-data.api-key}")
	private String apiKey;

	@Override
	public PensionStockRestResponse execute(RequestYearInfo requestYearInfo) {
		UriComponents uriComponents = UriComponentsBuilder
			.fromHttpUrl(requestYearInfo.getLink())
			.queryParam("serviceKey", apiKey)
			.queryParam("perPage", 1500)
			.build(true);
		ResponseEntity<PensionStockRestResponse> result = restTemplate.getForEntity(uriComponents.toUri(),
			PensionStockRestResponse.class);
		return result.getBody();
	}
}
