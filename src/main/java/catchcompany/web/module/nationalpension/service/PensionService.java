package catchcompany.web.module.nationalpension.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import catchcompany.web.module.nationalpension.controller.dto.InvestInfo;
import catchcompany.web.module.nationalpension.infrastructure.PensionYearStockJpaRepository;
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
