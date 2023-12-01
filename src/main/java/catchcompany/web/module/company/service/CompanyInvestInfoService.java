package catchcompany.web.module.company.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.CompanyInvestInfo;
import catchcompany.web.module.company.exception.NotExistCompany;
import catchcompany.web.module.company.repository.CompanyInvestInfoRepository;
import catchcompany.web.module.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyInvestInfoService {

	private final CompanyRepository companyRepository;
	private final CompanyInvestInfoRepository companyInvestInfoRepository;

	public void processCompanyInvestInfoToDatabase() {

		try {
			FileInputStream file = new FileInputStream("C:\\Users\\file\\BusinessReport.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0); //첫번째 시트탭
			int rows = sheet.getPhysicalNumberOfRows(); // 행의 수, 세로 길이를 모두 불러온다
			IntStream stream = IntStream.range(0,rows);
			stream.parallel().forEach( rowIndex -> {
				List<String> rowInfoList = new ArrayList<>();
				//행을읽는다
				HSSFRow row = sheet.getRow(rowIndex);
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					for (int colIndex = 0; colIndex <= cells; colIndex++) {
						//셀값을 읽는다
						HSSFCell cell = row.getCell(colIndex);
						String value = "";
						//셀이 빈값일경우를 위한 널체크
						if (cell == null) {
							continue;
						} else {
							//타입별로 내용 읽기
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
						}
						// System.out.println(rowIndex + "번 행 : " + colIndex + "번 열 값은: " + value);
						rowInfoList.add(value);
					} //컬럼 읽기 종료
				}
				if (rowIndex > 2 && rowInfoList.size() >= 16) {
						String investorName = rowInfoList.get(0).trim(); // 투자하는 회사명
						String investorCode = rowInfoList.get(1).trim(); // 투자하는 회사코드
						String investCompany = rowInfoList.get(4).trim(); // 투자받는 회사명
						String investDate = rowInfoList.get(5).trim(); // 투자 날짜
						String investTarget = rowInfoList.get(6).trim(); // 투자목적
						String basic1 = rowInfoList.get(8).trim(); // 기초 잔액 수량
						String basic2 = rowInfoList.get(9).trim(); // 기초잔액 지분율
						String basic3 = rowInfoList.get(10).trim(); // 기초잔액 장부가액
						String change1 = rowInfoList.get(11).trim(); // 증감수량
						String change2 = rowInfoList.get(12).trim(); // 증감 취득,처분 금액
						String change3 = rowInfoList.get(13).trim(); // 증감 평가손액
						String current1 = rowInfoList.get(14).trim(); // 기말잔액 수량
						String current2 = rowInfoList.get(15).trim(); // 기말잔액 지분율
						String current3 = rowInfoList.get(16).trim(); // 기말잔액 장부가액
						List<Company> companies = companyRepository.findByName(investorName.trim());

						if(companies != null) {
							CompanyInvestInfo companyInvestInfo = CompanyInvestInfo.builder()
								.company(companies.get(0))
								.investorName(investorName)
								.corporationCode(investorCode)
								.name(investCompany)
								.initialInvestmentDate(investDate)
								.basicStockCount(basic1)
								.basicStockShareRatio(basic2)
								.basicStockEvaluationValue(basic3)
								.currentStockCount(current1)
								.currentStockShareRatio(current2)
								.currentStockEvaluationValue(current3)
								.recentStockAmountOfChange(change1)
								.recentAcquisitionAmount(change2)
								.recentEvaluationGainsAndLosses(change3)
								.build();
							companyInvestInfoRepository.save(companyInvestInfo);
						}
				}
			});

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}