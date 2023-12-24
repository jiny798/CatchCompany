package catchcompany.web.module.company.service.admin;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.Invest;
import catchcompany.web.module.company.service.port.CompanyRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InvestDataProcessor {
	private final CompanyRepository companyRepository;

	public List<Invest> getInvestList(String path) { // "C:\\Users\\file\\BusinessReport.xls"
		List<Invest> investList = new ArrayList<>();
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
					//셀값을 읽는다
					HSSFCell cell = row.getCell(colIndex);
					String value = "";
					if (cell == null) continue;
					value = getCellValue(cell);
					rowList.add(value);
				} //컬럼 읽기 종료
			}
			if (!rowList.get(4).trim().equals("합계")
				&& !rowList.get(4).trim().equals("-")
				&& rowList.size() >= 18) {
				Invest invest = convertRowsToInvest(rowList); // 액셀 ROW 정보 한줄을 Invest 객체로 변환
				investList.add(invest);
			}
		});

		return investList;
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

	private Invest convertRowsToInvest(List<String> rowList) {
		String investorName = rowList.get(0).trim(); // 투자하는 회사명
		String investorCode = rowList.get(1).trim(); // 투자하는 회사 stock 코드
		String corpClass = "-";
		String investCompany = rowList.get(4).trim(); // 투자받는 회사명
		investCompany = investCompany.replaceAll("(주)", "");
		investCompany = investCompany.replaceAll("㈜", "");
		String investDate = rowList.get(5).trim(); // 투자 날짜
		String investTarget = rowList.get(6).trim(); // 투자목적
		if (investTarget.indexOf("투자") >= 0) {
			investTarget = "투자";
		}
		String change1 = rowList.get(11).trim(); // 증감수량
		String change2 = rowList.get(12).trim(); // 증감 취득,처분 금액
		String change3 = rowList.get(13).trim(); // 증감 평가손액
		String current1 = rowList.get(14).trim(); // 기말잔액 수량
		String current2 = rowList.get(15).trim(); // 기말잔액 지분율
		String current3 = rowList.get(16).trim(); // 기말잔액 장부가액

		List<Company> companies = companyRepository.findByName(investorName.trim());
		Company company = null;
		if (companies != null) {
			company = companies.get(0);
		}
		List<Company> corpList = companyRepository.findByName(investCompany);
		if (!corpList.isEmpty()) {
			for (Company c : corpList) {
				if (!c.getStockCode().isBlank()) {
					corpClass = "상장";
					break;
				}
			}
		}
		return Invest.builder()
			.company(company)
			.investorName(investorName)
			.corporationCode(investorCode)
			.name(investCompany)
			.corporationClass(corpClass)
			.investTarget(investTarget)
			.initialInvestmentDate(investDate)
			.currentStockCount(current1)
			.currentStockShareRatio(current2)
			.currentStockEvaluationValue(current3)
			.recentStockAmountOfChange(change1)
			.recentAcquisitionAmount(change2)
			.recentEvaluationGainsAndLosses(change3)
			.build();
	}


}
