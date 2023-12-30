package catchcompany.web.module.nationalpension.service.admin;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import catchcompany.web.module.common.service.port.ExcelClient;
import catchcompany.web.module.nationalpension.domain.PensionMonthStock;
import catchcompany.web.module.nationalpension.infrastructure.MonthStockFilePath;
import catchcompany.web.module.nationalpension.infrastructure.PensionMonthStockJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PensionMonthStockDataProcessor {

	private final PensionMonthStockJpaRepository pensionMonthStockJpaRepository;
	private final ExcelClient excelClient;
	private final MonthStockFilePath monthStockFilePath;

	public void executeSaveMonthStock() {
		List<PensionMonthStock> monthStocks
			= excelClient.getRowList(monthStockFilePath, (rowList) -> {
				return convertRowsToMonthStock(rowList);
		});

		for(PensionMonthStock stock : monthStocks) {
			pensionMonthStockJpaRepository.save(stock);
		}

	}


	private PensionMonthStock convertRowsToMonthStock(List<String> rowList) {
		String companyName = rowList.get(2);
		companyName = companyName.replaceAll("[(주)|㈜]", "");
		String date = rowList.get(3);
		String shareInAsset = rowList.get(4);
		log.info("--{}",shareInAsset);

		return PensionMonthStock.builder()
			.corporationName(companyName)
			.currentShareInAsset(Double.parseDouble(shareInAsset))
			.date(date)
			.build();
	}
}
