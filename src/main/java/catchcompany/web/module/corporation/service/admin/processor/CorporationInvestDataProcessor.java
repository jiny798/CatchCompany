package catchcompany.web.module.corporation.service.admin.processor;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import catchcompany.web.module.common.service.port.ExcelClient;
import catchcompany.web.module.common.service.port.FileClient;
import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.domain.InvestOfCorporation;
import catchcompany.web.module.corporation.infra.repository.CorporationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CorporationInvestDataProcessor {
	private final CorporationJpaRepository corporationJpaRepository;
	private Map<String, LinkedList<Corporation>> corporationMap;
	private final ExcelClient excelClient;
	private final FileClient fileClient;

	public List<InvestOfCorporation> getInvestList(MultipartFile multipartFile, int startRowNum, int endRowNum) {
		initMap();
		String filePath = fileClient.save(multipartFile);
		log.debug("file path = {}", filePath);
		List<InvestOfCorporation> investList =
			excelClient.getRowList(filePath, startRowNum, endRowNum, (rowList) -> {
				return convertRowsToInvest(rowList);
			});

		return investList;
	}

	/*
	 * Corporation 을 전부 조회하여 Map에 저장하는 초기화 메서드
	 * convertRowsToInvest() 메서드 내부에서 조회 속도 상승을 위해 사용하는 메서드
	 */
	private void initMap() {
		corporationMap = new LinkedHashMap<>();
		List<Corporation> corporationList = corporationJpaRepository.findAll();
		for (Corporation corporation : corporationList) {
			if (corporationMap.get(corporation.getName()) == null) {
				LinkedList list = new LinkedList();
				list.add(corporation);
				corporationMap.put(corporation.getName(), list);
			} else {
				LinkedList list = corporationMap.get(corporation.getName());
				list.add(corporation);
			}
		}

	}

	private InvestOfCorporation convertRowsToInvest(List<String> rowList) {
		String investorName = rowList.get(0).trim(); // 투자하는 회사명
		String investCompany = rowList.get(4).trim(); // 투자받는 회사명
		String investTarget = rowList.get(6).trim(); // 투자목적
		String corpClass = null;
		if (investTarget.indexOf("투자") >= 0) {
			investTarget = "투자";
		}

		List<Corporation> companies = corporationMap.get(investorName);
		Corporation corporation = null;
		if (companies != null && companies.size() != 0) {
			corporation = companies.get(0);
		}

		List<Corporation> corpList = corporationMap.get(investCompany);
		if (corpList != null) {
			for (Corporation c : corpList) {
				if (!c.getStockCode().isBlank()) { // Company에 StockCode가 존재하면 코스피 or 코스닥
					corpClass = "상장";
					break;
				}
			}
		}
		return InvestOfCorporation.builder()
			.corporation(corporation)
			.investorName(investorName) // 투자하는 회사
			.corporationCode(rowList.get(1).trim())
			.name(investCompany) // 투자받는 회사
			.corporationClass(corpClass)
			.investTarget(investTarget)
			.initialInvestmentDate(rowList.get(5).trim())
			.currentStockCount(rowList.get(14).trim())  // 현재 주식 정보
			.currentStockShareRatio(rowList.get(15).trim())
			.currentStockEvaluationValue(rowList.get(16).trim())
			.recentStockAmountOfChange(rowList.get(11).trim()) // 최근 증감 정보
			.recentAcquisitionAmount(rowList.get(12).trim())
			.recentEvaluationGainsAndLosses(rowList.get(13).trim())
			.build();
	}

}
