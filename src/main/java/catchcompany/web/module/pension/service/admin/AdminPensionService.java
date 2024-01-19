package catchcompany.web.module.pension.service.admin;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import catchcompany.web.module.common.service.port.FileClient;
import catchcompany.web.module.pension.controller.dto.InvestYearInfo;
import catchcompany.web.module.pension.service.admin.processor.PensionQuarterStockDataProcessor;
import catchcompany.web.module.pension.service.admin.processor.PensionYearStockDataProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminPensionService {

	private final FileClient fileClient;
	private final PensionYearStockDataProcessor pensionYearStockDataProcessor;
	private final PensionQuarterStockDataProcessor pensionQuarterStockDataProcessor;

	public void processInvestInfoSave(InvestYearInfo investYearInfo) {
		pensionYearStockDataProcessor.saveInvestInfo(investYearInfo);
	}

	public void processInvestInfoSort(int year) {
		pensionYearStockDataProcessor.sortInvestInfo(year);
	}

	public void processSaveQuarterInvest(MultipartFile file) {
		log.info("multipartFile={}", file);
		String fullPath = fileClient.save(file);
		pensionQuarterStockDataProcessor.executeSaveQuarterStock(fullPath);
	}

}
