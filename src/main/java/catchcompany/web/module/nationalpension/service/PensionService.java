package catchcompany.web.module.nationalpension.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import catchcompany.web.module.nationalpension.controller.dto.InvestInfo;
import catchcompany.web.module.nationalpension.repository.SortInvestInfoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PensionService {

	private final SortInvestInfoRepository sortInvestInfoRepository;

	public List<InvestInfo> getInvestInfo(int year) {
		List<InvestInfo> list = sortInvestInfoRepository.findByYear(year).stream()
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
