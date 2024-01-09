package catchcompany.web.module.pension.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import catchcompany.web.module.pension.controller.dto.InvestInfo;
import catchcompany.web.module.pension.infra.repository.PensionYearStockJpaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PensionService {

	private final PensionYearStockJpaRepository pensionYearStockJpaRepository;

	public List<InvestInfo> getInvestInfo(int year) {
		List<InvestInfo> list = pensionYearStockJpaRepository.findByYear(year).stream()
			.map(sortInvestInfo -> new InvestInfo(
				sortInvestInfo.getCorporationName(),
				sortInvestInfo.getBeforeShareInAsset(),
				sortInvestInfo.getCurrentShareInAsset()
			)).
			limit(20).
			collect(Collectors.toList());
		return list;
	}
}
