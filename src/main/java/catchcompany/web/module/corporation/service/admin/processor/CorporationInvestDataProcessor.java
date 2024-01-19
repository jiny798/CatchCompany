package catchcompany.web.module.corporation.service.admin.processor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

	public List<InvestOfCorporation> getInvestList(MultipartFile multipartFile) {
		initMap();
		String filePath = fileClient.save(multipartFile);
		List<InvestOfCorporation> investList =
			excelClient.getRowList(filePath, (rowList) -> {
				if (isCorporationData(rowList)) {
					return convertRowsToInvest(rowList);
				}
				return null;
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

	/*
	 * 행의 길이가 18 이상
	 * 셀에 "합계", "-" 문자가 포함되는 경우는 데이터 정보에서 제외한다
	 */
	private boolean isCorporationData(List<String> rowList) {
		return rowList.size() >= 18 &&
			!rowList.get(4).trim().equals("합계") &&
			!rowList.get(4).trim().equals("-");
	}

	private InvestOfCorporation convertRowsToInvest(List<String> rowList) {
		String investorName = rowList.get(0).trim(); // 투자하는 회사명
		String corpClass = "";
		String investCompany = rowList.get(4).trim(); // 투자받는 회사명
		investCompany = investCompany.replaceAll("[(주)|㈜]", "");
		String investTarget = rowList.get(6).trim(); // 투자목적
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
