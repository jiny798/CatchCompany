package catchcompany.web.module.nationalpension.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import catchcompany.web.module.nationalpension.controller.dto.InvestYearInfo;
import catchcompany.web.module.nationalpension.domain.PensionInvestInfo;
import catchcompany.web.module.nationalpension.domain.SortInvestInfo;
import catchcompany.web.module.nationalpension.exception.ProcessedRequestException;
import catchcompany.web.module.nationalpension.repository.PensionInvestInfoRepository;
import catchcompany.web.module.nationalpension.repository.SortInvestInfoRepository;
import catchcompany.web.module.nationalpension.service.dto.PensionInvestInfoDto;
import catchcompany.web.module.nationalpension.service.dto.PensionInvestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PensionApiClient {

	private final PensionInvestInfoRepository pensionInvestInfoRepository;
	private final SortInvestInfoRepository sortInvestInfoRepository;

	@Value("${open-data.auth-key}")
	private String authKey;

	@Value("${open-data.api-key}")
	private String apiKey;

	public void saveInvestInfo(InvestYearInfo investYearInfo) {
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

	public void sortInvestInfo(int year) {
		List<SortInvestInfo> sortInvestInfos = sortInvestInfoRepository.findByYear(year);
		if(!sortInvestInfos.isEmpty()) {
			throw new ProcessedRequestException("이미 처리된 년도 입니다.");
		}

		List<PensionInvestInfo> investInfoList = pensionInvestInfoRepository.findByYear(year);
		List<SortInvestInfo> sortList = new ArrayList<>();
		for (PensionInvestInfo currentInfo : investInfoList) {
			String corpName = currentInfo.getCorporationName(); // 현재회사이름
			List<PensionInvestInfo> findList = pensionInvestInfoRepository.findByYearAndCorporationName(year - 1,
				corpName); // 이전년도 정보 가져오기
			if (findList.isEmpty()) {
				continue;
			} else {
				PensionInvestInfo beforeInfo = findList.get(0);
				SortInvestInfo sortInvestInfo = SortInvestInfo.builder()
					.corporationName(currentInfo.getCorporationName())
					.evaluation(currentInfo.getEvaluation()) // 이전년도 평가액 차이를 구한다
					.shareInAsset(Double.parseDouble(currentInfo.getShareInAsset()) - Double.parseDouble(beforeInfo.getShareInAsset()))
					.shareRatio(currentInfo.getShareRatio())
					.year(year)
					.build();
				sortList.add(sortInvestInfo); // 이전년도 평가액 차이 구한 회사만 list 추가
			}
		}

		Collections.sort(sortList, (investInfo1, investInfo2) -> {
			if (investInfo1.getShareInAsset() < investInfo2.getShareInAsset()) {
				return -1;
			} else if (investInfo1.getShareInAsset() == investInfo2.getShareInAsset()) {
				return 0;
			} else if(investInfo1.getShareInAsset() > investInfo2.getShareInAsset()){
				return 1;
			}
			return 0;
		});

		for (SortInvestInfo sortInvestInfo : sortList) {
			sortInvestInfoRepository.save(sortInvestInfo);
		}
	}
}

