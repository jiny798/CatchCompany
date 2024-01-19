package catchcompany.web.module.pension.service.admin.processor;

import java.util.List;

import org.springframework.stereotype.Service;

import catchcompany.web.module.common.service.port.ExcelClient;
import catchcompany.web.module.pension.domain.PensionQuarterStock;
import catchcompany.web.module.pension.infra.repository.PensionQuarterStockJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PensionQuarterStockDataProcessor {

	private final PensionQuarterStockJpaRepository pensionQuarterStockJpaRepository;
	private final ExcelClient excelClient;

	public void executeSaveQuarterStock(String filePath) {
		List<PensionQuarterStock> monthStocks
			= excelClient.getRowList(filePath, (rowList) -> {
			return convertRowsToMonthStock(rowList);
		});
		for (PensionQuarterStock stock : monthStocks) {
			pensionQuarterStockJpaRepository.save(stock);
		}
	}

	private PensionQuarterStock convertRowsToMonthStock(List<String> rowList) {
		String companyName = rowList.get(2);
		companyName = companyName.replaceAll("[(주)|㈜]", "");
		String date = rowList.get(3);
		String shareInAsset = rowList.get(4);

		return PensionQuarterStock.builder()
			.corporationName(companyName)
			.currentShareInAsset(Double.parseDouble(shareInAsset))
			.date(date)
			.build();
	}
}
