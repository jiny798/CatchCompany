package catchcompany.web.module.pension.service.admin;

import org.springframework.stereotype.Service;

import catchcompany.web.module.pension.controller.dto.InvestYearInfo;
import catchcompany.web.module.pension.service.admin.processor.PensionQuarterStockDataProcessor;
import catchcompany.web.module.pension.service.admin.processor.PensionYearStockDataProcessor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminPensionService {

	private final PensionYearStockDataProcessor pensionYearStockDataProcessor;
	private final PensionQuarterStockDataProcessor pensionQuarterStockDataProcessor;

	public void processInvestInfoSave(InvestYearInfo investYearInfo) {
		pensionYearStockDataProcessor.saveInvestInfo(investYearInfo);
	}

	public void processInvestInfoSort(int year) {
		pensionYearStockDataProcessor.sortInvestInfo(year);
	}

	public void processSaveMonthInvest() {
		pensionQuarterStockDataProcessor.executeSaveMonthStock();
	}

}
