package catchcompany.web.module.pension.service.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import catchcompany.web.module.common.service.port.FileClient;
import catchcompany.web.module.pension.controller.dto.InvestYearInfo;
import catchcompany.web.module.pension.domain.PensionQuarterStock;
import catchcompany.web.module.pension.domain.PensionYearStock;
import catchcompany.web.module.pension.infra.repository.PensionQuarterStockJpaRepository;
import catchcompany.web.module.pension.infra.repository.PensionYearStockJpaRepository;
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
	private final PensionQuarterStockJpaRepository pensionQuarterStockJpaRepository;
	private final PensionYearStockJpaRepository pensionYearStockJpaRepository;

	public void processInvestInfoSave(List<InvestYearInfo> investYearInfoList) {
		investYearInfoList.stream().forEach(investYearInfo -> {
			if(!pensionYearStockJpaRepository.findByYear(investYearInfo.getYear()).isEmpty()) { // 이미 추가된 년도면 무시한다
				return;
			}
			List<PensionYearStock> pensionYearStockList = pensionYearStockDataProcessor.getPensionYearStockList(
				investYearInfo);
			for (PensionYearStock stock : pensionYearStockList) {
				pensionYearStockJpaRepository.save(stock);
			}
			pensionYearStockDataProcessor.sortByShareRatio(investYearInfo.getYear()); // 저장 후 정렬
		});

	}

	public void processSaveQuarterInvest(MultipartFile file) {
		String fullPath = fileClient.save(file);
		List<PensionQuarterStock> quarterStocks = pensionQuarterStockDataProcessor.executeSaveQuarterStock(fullPath);
		for (PensionQuarterStock stock : quarterStocks) {
			pensionQuarterStockJpaRepository.save(stock);
		}
	}

}
