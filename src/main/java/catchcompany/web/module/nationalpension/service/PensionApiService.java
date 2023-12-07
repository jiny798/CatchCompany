package catchcompany.web.module.nationalpension.service;

import java.net.URI;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import catchcompany.web.module.nationalpension.repository.PensionInvestInfoRepository;
import catchcompany.web.module.nationalpension.service.dto.PensionInvestInfoDto;
import catchcompany.web.module.nationalpension.service.dto.PensionInvestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PensionApiService {

	private final PensionInvestInfoRepository pensionInvestInfoRepository;

	@Value("${open-data.auth-key}")
	private String authKey;

	@Value("${open-data.api-key}")
	private String apiKey;

	private String[] url = { // 2017년도 말부터 ~ 2022년 말
		"https://api.odcloud.kr/api/3070507/v1/uddi:f80ea05e-e6d8-46db-8e43-dd07f741defb_201812140921",
		"https://api.odcloud.kr/api/3070507/v1/uddi:d879fc1f-4132-4d64-98b0-931fdab7c092",
		"https://api.odcloud.kr/api/3070507/v1/uddi:b983a74e-0c70-4258-8e27-9381a5215b9c",
		"https://api.odcloud.kr/api/3070507/v1/uddi:ff42d7e9-283d-4247-9f7f-ccc607743dee",
		"https://api.odcloud.kr/api/3070507/v1/uddi:d88b7e3b-b26c-4cee-887c-8a256cce8a56",
		"https://api.odcloud.kr/api/3070507/v1/uddi:c3ae1435-165c-42f4-bf8d-cc26f68e71f6"};

	public void processInvestInfoSave() {
		RestTemplate restTemplate = new RestTemplate();

		String decoded = null;
		String encoded = null;
		UriComponents uriComponents = UriComponentsBuilder
			.fromHttpUrl(url[0])
			.queryParam("serviceKey",apiKey)
			.build(true);

		log.info("요청url {}", uriComponents.toUri());

		ResponseEntity<PensionInvestResult> result = restTemplate.getForEntity(uriComponents.toUri(), PensionInvestResult.class);

		log.info("응답 {}", result.getBody().totalCount);
		List<PensionInvestInfoDto> list = result.getBody().getData();
		log.info("응답 {}", list.size());


	}
}
