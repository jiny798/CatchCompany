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

import catchcompany.web.module.nationalpension.controller.dto.InvestYearInfo;
import catchcompany.web.module.nationalpension.domain.PensionInvestInfo;
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

	public void processInvestInfoSave(InvestYearInfo investYearInfo) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponents uriComponents = UriComponentsBuilder
			.fromHttpUrl(investYearInfo.getLink())
			.queryParam("serviceKey", apiKey)
			.queryParam("perPage", 1500)
			.build(true);
		ResponseEntity<PensionInvestResult> result = restTemplate.getForEntity(uriComponents.toUri(),
			PensionInvestResult.class);
		List<PensionInvestInfoDto> list = result.getBody().getData();

		for (PensionInvestInfoDto dto : list) {
			PensionInvestInfo info = PensionInvestInfo.builder()
				.corporationName(dto.getName())
				.evaluation(dto.getEvaluation())
				.shareInAsset(dto.getShareInAsset())
				.shareRatio(dto.getShareRatio())
				.year(investYearInfo.getYear())
				.build();
			pensionInvestInfoRepository.save(info);
		}

	}
}

