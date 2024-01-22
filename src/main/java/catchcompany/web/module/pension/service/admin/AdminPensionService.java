package catchcompany.web.module.pension.service.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

	/*
	 * 년도별 투자 리스트를 자산 비중을 내림차순으로 정렬하여 저장
	 */
	public void processInvestInfoSave(List<InvestYearInfo> investYearInfoList) {
		investYearInfoList.stream().forEach(investYearInfo -> {
			if (!pensionYearStockJpaRepository.findByYear(investYearInfo.getYear()).isEmpty()) { // 이미 추가된 년도면 무시한다
				return;
			}
			List<PensionYearStock> pensionYearStockList = pensionYearStockDataProcessor.getPensionYearStockList(
				investYearInfo);
			// 자산 비중을 내림차순으로 정렬
			pensionYearStockList = pensionYearStockDataProcessor.sortByShareRatio(pensionYearStockList,
				investYearInfo.getYear());
			for (PensionYearStock stock : pensionYearStockList) {
				pensionYearStockJpaRepository.save(stock);
			}
		});

	}

	public void processSaveQuarterInvest(MultipartFile file, int beforeYear) {
		String fullPath = fileClient.save(file);
		List<PensionQuarterStock> quarterStocks = pensionQuarterStockDataProcessor.executeSaveQuarterStock(fullPath);
		pensionQuarterStockDataProcessor.sortByShareRatio(quarterStocks, beforeYear);
		for (PensionQuarterStock stock : quarterStocks) {
			pensionQuarterStockJpaRepository.save(stock);
		}
	}

	public List<String> checkExistCorporationName(MultipartFile file, int beforeYear) {
		String fullPath = fileClient.save(file);
		List<PensionQuarterStock> quarterStocks = pensionQuarterStockDataProcessor.executeSaveQuarterStock(fullPath);
		List<String> notExistNameList = new ArrayList<>();

		for (PensionQuarterStock stock : quarterStocks) {
			int year = Integer.parseInt(stock.getDate().substring(0, 4));
			log.info("check {} - {}", year, stock.getCorporationName());
			List<PensionYearStock> findStockList = pensionYearStockJpaRepository.findByYearAndCorporationName(
				beforeYear,
				stock.getCorporationName());
			if (findStockList.isEmpty()) {
				notExistNameList.add(stock.getCorporationName());
			}
		}
		boolean tag = fileClient.remove(fullPath);

		return notExistNameList;
	}

}
