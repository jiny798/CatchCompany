package catchcompany.web.module.company.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import catchcompany.web.module.company.infrastructure.dto.CompanyDataResponse;
import catchcompany.web.module.company.service.port.CompanyDataRestClient;
import catchcompany.web.module.pension.controller.dto.InvestYearInfo;
import catchcompany.web.module.pension.infrastructure.dto.PensionStockRestResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyDataRestClientImpl implements CompanyDataRestClient {

	private final RestTemplate restTemplate;
	private int perPage = 4000;

	@Value("${open-data.api-key}")
	private String apiKey;

	public int getTotalPageCount(String link) {
		UriComponents uriComponents = UriComponentsBuilder
			.fromHttpUrl(link)
			.queryParam("serviceKey", apiKey)
			.queryParam("page", 1)
			.queryParam("perPage", perPage)
			.build(true);
		ResponseEntity<CompanyDataResponse> result = restTemplate.getForEntity(uriComponents.toUri(),
			CompanyDataResponse.class);
		int totalPage = result.getBody().totalCount;
		int count = totalPage / perPage + (totalPage % perPage > 0 ? 1 : 0);
		return count;
	}

	@Override
	public CompanyDataResponse execute(String link, int page) {
		UriComponents uriComponents = UriComponentsBuilder
			.fromHttpUrl(link)
			.queryParam("serviceKey", apiKey)
			.queryParam("page", page)
			.queryParam("perPage", perPage)
			.build(true);
		ResponseEntity<CompanyDataResponse> result = restTemplate.getForEntity(uriComponents.toUri(),
			CompanyDataResponse.class);
		return result.getBody();
	}
}
