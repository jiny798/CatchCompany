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
	private final String path = "C:\\Users\\file\\BusinessReport.xls";
	private Map<String, LinkedList<Corporation>> corporationMap;

	public List<InvestOfCorporation> getInvestList() {
		List<InvestOfCorporation> investList = new CopyOnWriteArrayList<>();
		initMap();
		FileInputStream file = null;
		HSSFWorkbook workbook = null;
		try {
			file = new FileInputStream(path);
			workbook = new HSSFWorkbook(file);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		HSSFSheet sheet = workbook.getSheetAt(0); //첫번째 시트탭
		int rows = sheet.getPhysicalNumberOfRows(); // 행의 길이를 모두 불러온다
		IntStream stream = IntStream.range(0, rows);

		stream.parallel().forEach(rowIndex -> {
			List<String> rowList = new ArrayList<>();
			HSSFRow row = sheet.getRow(rowIndex); //행 읽기
			if (row != null) {
				int cells = row.getPhysicalNumberOfCells();
				for (int colIndex = 0; colIndex <= cells; colIndex++) {
					HSSFCell cell = row.getCell(colIndex); //셀값을 읽는다
					if (cell == null)
						continue;
					rowList.add(getCellValue(cell));
				} //컬럼 읽기 종료
			}
			if (isCorporationData(rowList)) {
				InvestOfCorporation invest = convertRowsToInvest(rowList); // 액셀 ROW 정보 한줄을 Invest 객체로 변환
				investList.add(invest);
			}
		});

		return investList;
	}

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

	private boolean isCorporationData(List<String> rowList) {
		return rowList.size() >= 18 &&
			!rowList.get(4).trim().equals("합계") &&
			!rowList.get(4).trim().equals("-");
	}

	private String getCellValue(HSSFCell cell) {
		String value = "";
		switch (cell.getCellType()) {
			case FORMULA:
				value = cell.getCellFormula();
				break;
			case NUMERIC:
				value = cell.getNumericCellValue() + "";
				break;
			case STRING:
				value = cell.getStringCellValue() + "";
				break;
			case BLANK:
				value = cell.getBooleanCellValue() + "";
				break;
			case ERROR:
				value = cell.getErrorCellValue() + "";
				break;
		}
		return value;
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

		// List<Corporation> companies = corporationJpaRepository.findByName(investorName.trim());
		List<Corporation> companies = corporationMap.get(investorName.trim());
		Corporation corporation = null;
		if (companies != null && companies.size() != 0) {
			corporation = companies.get(0);
		}
		// List<Corporation> corpList = corporationJpaRepository.findByName(investCompany);
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
