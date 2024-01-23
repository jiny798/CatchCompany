package catchcompany.web.module.pension.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import catchcompany.web.module.common.service.port.FileClient;
import catchcompany.web.module.pension.controller.dto.RequestYearInfo;
import catchcompany.web.module.pension.domain.PensionQuarterStock;
import catchcompany.web.module.pension.domain.PensionYearStock;
import catchcompany.web.module.pension.infra.repository.PensionQuarterStockJpaRepository;
import catchcompany.web.module.pension.infra.repository.PensionYearStockJpaRepository;
import catchcompany.web.module.pension.service.processor.PensionQuarterStockDataProcessor;
import catchcompany.web.module.pension.service.processor.PensionYearStockDataProcessor;
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
	public void processInvestInfoSave(List<RequestYearInfo> requestYearInfoList) {
		requestYearInfoList.stream().forEach(investYearInfo -> {
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

	public void processSaveQuarterInvest(MultipartFile file, String quarter, int beforeYear) {
		String fullPath = fileClient.save(file);
		List<PensionQuarterStock> quarterStocks = pensionQuarterStockDataProcessor.executeSaveQuarterStock(fullPath,
			quarter);
		pensionQuarterStockDataProcessor.sortByShareRatio(quarterStocks, beforeYear);
		for (PensionQuarterStock stock : quarterStocks) {
			pensionQuarterStockJpaRepository.save(stock);
		}
	}

	public List<String> checkExistCorporationName(MultipartFile file, int beforeYear) {
		String fullPath = fileClient.save(file);
		List<PensionQuarterStock> quarterStocks = pensionQuarterStockDataProcessor.executeSaveQuarterStock(fullPath,
			null);
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
