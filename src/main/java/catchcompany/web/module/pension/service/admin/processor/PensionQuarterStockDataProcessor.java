package catchcompany.web.module.pension.service.admin.processor;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import catchcompany.web.module.common.service.port.ExcelClient;
import catchcompany.web.module.pension.domain.PensionQuarterStock;
import catchcompany.web.module.pension.domain.PensionYearStock;
import catchcompany.web.module.pension.infra.repository.PensionQuarterStockJpaRepository;
import catchcompany.web.module.pension.infra.repository.PensionYearStockJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PensionQuarterStockDataProcessor {

	private final PensionYearStockJpaRepository pensionYearStockJpaRepository;
	private final ExcelClient excelClient;

	public List<PensionQuarterStock> executeSaveQuarterStock(String filePath) {
		List<PensionQuarterStock> stocks
			= excelClient.getRowList(filePath, (rowList) -> {
			return convertRowsToMonthStock(rowList);
		});
		return stocks;
	}

	private PensionQuarterStock convertRowsToMonthStock(List<String> rowList) {
		String companyName = rowList.get(2);
		companyName = companyName.replace("(주)", "");
		companyName = companyName.replace("㈜", "");
		String date = rowList.get(3);
		String shareInAsset = rowList.get(4);

		return PensionQuarterStock.builder()
			.corporationName(companyName)
			.currentShareRatio(Double.parseDouble(shareInAsset))
			.date(date)
			.build();
	}

	public List<PensionQuarterStock> sortByShareRatio(List<PensionQuarterStock> quarterStockList, int beforeYear) {
		for (PensionQuarterStock quarterStock : quarterStockList) {
			int year = Integer.parseInt(quarterStock.getDate().substring(0, 4));
			List<PensionYearStock> beforeYearStockList =
				pensionYearStockJpaRepository.findByYearAndCorporationName(beforeYear,
					quarterStock.getCorporationName());

			if (beforeYearStockList.size() == 0) {
				quarterStock.setBeforeShareRatio(0.0);
				quarterStock.setChangeShareRatio(quarterStock.getCurrentShareRatio());
			} else {
				PensionYearStock beforeYearStock = beforeYearStockList.get(0);
				quarterStock.setBeforeShareRatio(beforeYearStock.getShareRatio());
				quarterStock.setChangeShareRatio(
					quarterStock.getCurrentShareRatio() - beforeYearStock.getShareRatio());
			}

		}
		sortByShareInAsset(quarterStockList);
		return quarterStockList;
	}

	/*
	 * 자산내 비중 퍼센트를 기준으로 오름차순 정렬
	 */
	private void sortByShareInAsset(List<PensionQuarterStock> sortList) {
		Collections.sort(sortList, (investInfo1, investInfo2) -> {
			if (investInfo1.getChangeShareRatio() < investInfo2.getChangeShareRatio()) {
				return 1;
			} else if (investInfo1.getChangeShareRatio() == investInfo2.getChangeShareRatio()) {
				return 0;
			} else if (investInfo1.getChangeShareRatio() > investInfo2.getChangeShareRatio()) {
				return -1;
			}
			return 0;
		});
	}
}
