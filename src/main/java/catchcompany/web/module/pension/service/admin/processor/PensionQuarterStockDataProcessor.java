package catchcompany.web.module.pension.service.admin.processor;

import java.util.List;
import org.springframework.stereotype.Service;

import catchcompany.web.module.common.service.port.ExcelClient;
import catchcompany.web.module.pension.domain.PensionMonthStock;
import catchcompany.web.module.pension.infra.repository.PensionMonthStockJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PensionQuarterStockDataProcessor {

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

		return PensionMonthStock.builder()
			.corporationName(companyName)
			.currentShareInAsset(Double.parseDouble(shareInAsset))
			.date(date)
			.build();
	}
}
