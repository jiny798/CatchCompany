package catchcompany.web.module.common.infra;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import java.util.function.Function;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import catchcompany.web.module.common.service.port.ExcelClient;
import catchcompany.web.module.common.service.port.FileClient;

@Component
public class ExcelClientImpl<R> implements ExcelClient {

	@Override
	public <R> List<R> getRowList(String filePath, Function<List<String>, R> convert) {
		List<R> rowList = new CopyOnWriteArrayList<>();
		Sheet sheet = getSheet(filePath); //첫번째 시트탭
		int rows = sheet.getPhysicalNumberOfRows(); // 행의 길이를 모두 불러온다
		IntStream stream = IntStream.range(0, rows);
		stream.parallel().forEach(rowIndex -> {
			List<String> columList = new ArrayList<>();
			Row row = sheet.getRow(rowIndex); //행 읽기
			transferRowToList(row, columList);
			R target = convert.apply(columList);
			rowList.add(target);
		});
		return rowList;
	}

	@Override
	public <R> List<R> getRowList(String filePath, int startRowNum, int endRowNum, Function<List<String>, R> function) {
		List<R> rowList = new CopyOnWriteArrayList<>();
		Sheet sheet = getSheet(filePath); //첫번째 시트탭
		int rows = sheet.getPhysicalNumberOfRows(); // 행의 길이를 모두 불러온다
		IntStream stream = IntStream.rangeClosed(startRowNum, endRowNum);
		stream.parallel().forEach(rowIndex -> {
			List<String> columList = new ArrayList<>();
			Row row = sheet.getRow(rowIndex); //행 읽기
			transferRowToList(row, columList);
			R target = function.apply(columList);
			rowList.add(target);
		});
		return rowList;
	}

	private Sheet getSheet(String filePath) {
		try {
			FileInputStream file = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheetAt(0);
			return sheet;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void transferRowToList(Row row, List<String> list) {
		if (row != null) {
			int cells = row.getPhysicalNumberOfCells();
			for (int colIndex = 0; colIndex <= cells; colIndex++) {
				//셀값을 읽는다
				Cell cell = row.getCell(colIndex);
				String value = "";
				if (cell == null)
					continue;
				value = getCellValue(cell);
				list.add(value);
			}
		}
	}

	private String getCellValue(Cell cell) {
		String value = "";
		switch (cell.getCellType()) {
			case FORMULA:
				value = cell.getCellFormula();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					value = new SimpleDateFormat("yyyy-MM-dd").format(date);
					break;
				}
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

}
