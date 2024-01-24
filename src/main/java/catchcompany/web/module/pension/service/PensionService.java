package catchcompany.web.module.pension.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import catchcompany.web.module.pension.controller.dto.InvestInfo;
import catchcompany.web.module.pension.controller.dto.QuarterInvestInfo;
import catchcompany.web.module.pension.infra.repository.PensionQuarterStockJpaRepository;
import catchcompany.web.module.pension.infra.repository.PensionYearStockJpaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PensionService {

	private final PensionYearStockJpaRepository pensionYearStockJpaRepository;
	private final PensionQuarterStockJpaRepository pensionQuarterStockJpaRepository;

	public List<InvestInfo> getInvestInfo(int year) {
		List<InvestInfo> list = pensionYearStockJpaRepository.findByYear(year).stream()
			.map(sortInvestInfo -> new InvestInfo(
				sortInvestInfo.getCorporationName(),
				sortInvestInfo.getBeforeShareInAsset(),
				sortInvestInfo.getCurrentShareInAsset(),
				String.format("%.2f", sortInvestInfo.getChangeShareInAsset())
			)).collect(Collectors.toList());
		return list;
	}

	public List<QuarterInvestInfo> getQuarterInvestInfo(String quarter) {
		List<QuarterInvestInfo> list = pensionQuarterStockJpaRepository.findByQuarter(quarter).stream()
			.map(quarterStock -> new QuarterInvestInfo(
				quarterStock.getCorporationName(),
				quarterStock.getBeforeShareRatio(),
				quarterStock.getCurrentShareRatio(),
				String.format("%.2f", quarterStock.getChangeShareRatio())
			)).collect(Collectors.toList());
		return list;
	}
}
