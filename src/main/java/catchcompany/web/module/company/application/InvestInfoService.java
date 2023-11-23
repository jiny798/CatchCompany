package catchcompany.web.module.company.application;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.InvestCompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class InvestInfoService {

	private final InvestCompanyRepository investCompanyRepository;
	@Value("${dart.api-key}")
	private String apiKey;

	public void processCompanyInvestInfoToDatabase(Company company) {
		// log.info(company.getCorporationCode() + " : 회사 조회");
		try {
			FileInputStream file = new FileInputStream("C:\\Users\\file\\BusinessReport.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			int rowIndex = 0;
			int colIndex = 0;
			HSSFSheet sheet = workbook.getSheetAt(0); //첫번째 시트탭
			int rows = sheet.getPhysicalNumberOfRows(); // 행의 수, 세로 길이를 모두 불러온다
			for (rowIndex = 0; rowIndex <= rows; rowIndex++) {
				//행을읽는다
				HSSFRow row = sheet.getRow(rowIndex);
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					for (colIndex = 0; colIndex <= cells; colIndex++) {
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
						System.out.println(rowIndex + "번 행 : " + colIndex + "번 열 값은: " + value);
					}

				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
