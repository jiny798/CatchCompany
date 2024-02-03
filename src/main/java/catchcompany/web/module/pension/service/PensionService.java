package catchcompany.web.module.pension.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import catchcompany.web.module.common.infra.PageManager;
import catchcompany.web.module.pension.controller.dto.response.QuarterInvestInfo;
import catchcompany.web.module.pension.controller.dto.response.QuarterInvestResponse;
import catchcompany.web.module.pension.controller.dto.response.YearInvestInfo;
import catchcompany.web.module.pension.controller.dto.response.YearInvestResponse;
import catchcompany.web.module.pension.infra.repository.PensionQuarterStockJpaRepository;
import catchcompany.web.module.pension.infra.repository.PensionYearStockJpaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PensionService {

	private final PensionYearStockJpaRepository pensionYearStockJpaRepository;
	private final PensionQuarterStockJpaRepository pensionQuarterStockJpaRepository;
	private final PageManager pageManager;

	public YearInvestResponse findInvestInfoByYear(int year, Pageable pageable) {
		Page<YearInvestInfo> page = pensionYearStockJpaRepository.findPageByYear(year, pageable)
			.map(pensionYearStock -> new YearInvestInfo(
				pensionYearStock.getCorporationName(),
				pensionYearStock.getBeforeShareInAsset(),
				pensionYearStock.getCurrentShareInAsset(),
				String.format("%.2f", pensionYearStock.getChangeShareInAsset())
			));

		YearInvestResponse response = new YearInvestResponse(page,
			pageManager.generatePageInfo(page, pageable.getPageNumber(), pageable.getPageSize()));

		return response;
	}

	public QuarterInvestResponse findQuarterInvestInfoByQuarter(String quarter, Pageable pageable) {
		Page<QuarterInvestInfo> page = pensionQuarterStockJpaRepository.findPageByQuarter(quarter, pageable)
			.map(quarterStock -> new QuarterInvestInfo(
				quarterStock.getCorporationName(),
				quarterStock.getBeforeShareRatio(),
				quarterStock.getCurrentShareRatio(),
				String.format("%.2f", quarterStock.getChangeShareRatio())
			));

		QuarterInvestResponse response = new QuarterInvestResponse(page,
			pageManager.generatePageInfo(page, pageable.getPageNumber(), pageable.getPageSize()));

		return response;
	}
}
