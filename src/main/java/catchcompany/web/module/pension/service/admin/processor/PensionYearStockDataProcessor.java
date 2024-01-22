package catchcompany.web.module.pension.service.admin.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import catchcompany.web.module.pension.infra.dto.PensionStockDto;
import catchcompany.web.module.pension.infra.dto.PensionStockRestResponse;
import catchcompany.web.module.pension.controller.dto.InvestYearInfo;
import catchcompany.web.module.pension.domain.PensionYearStock;
import catchcompany.web.module.pension.infra.repository.PensionYearStockJpaRepository;
import catchcompany.web.module.pension.service.port.PensionStockRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PensionYearStockDataProcessor {

	private final PensionYearStockJpaRepository pensionYearStockJpaRepository;
	private final PensionStockRestClient pensionStockRestClient;

	public List<PensionYearStock> getPensionYearStockList(InvestYearInfo investYearInfo) {
		PensionStockRestResponse pensionStockRestResponse = pensionStockRestClient.execute(investYearInfo);
		List<PensionStockDto> pensionStockDtoList = pensionStockRestResponse.getData();
		List<PensionYearStock> pensionYearStockList = new ArrayList<>();
		for (PensionStockDto dto : pensionStockDtoList) {
			PensionYearStock info = PensionYearStock.builder()
				.corporationName(dto.getName())
				.evaluation(dto.getEvaluation())
				.currentShareInAsset(dto.getShareInAsset())
				.shareRatio(dto.getShareRatio())
				.year(investYearInfo.getYear())
				.build();
			pensionYearStockList.add(info);
		}

		return pensionYearStockList;
	}

	public List<PensionYearStock> sortByShareRatio(List<PensionYearStock> pensionYearStockList, int year) {
		List<PensionYearStock> beforeYearStockList = pensionYearStockJpaRepository.findByYear(year - 1);
		if (beforeYearStockList.isEmpty()) { // 첫번째 년도는 정렬하지 않는다
			return pensionYearStockList;
		}

		for (PensionYearStock currentYearStock : pensionYearStockList) {
			// year 년도 회사이름 가져오기
			String corpName = currentYearStock.getCorporationName();
			// 이전년도 회사정보 가져오기
			List<PensionYearStock> beforeStockList = pensionYearStockJpaRepository.findByYearAndCorporationName(
				year - 1,
				corpName);

			if (beforeStockList.size() == 0) {
				// 이전 년도가 없으면, 새로운 회사로 취급
				currentYearStock.setBeforeShareInAsset(currentYearStock.getCurrentShareInAsset());
				currentYearStock.setChangeShareInAsset(0.0);
			} else {
				PensionYearStock beforeYearStock = beforeStockList.get(0);
				currentYearStock.setBeforeShareInAsset(beforeYearStock.getCurrentShareInAsset());
				currentYearStock.setChangeShareInAsset(
					currentYearStock.getCurrentShareInAsset() - beforeYearStock.getCurrentShareInAsset());
			}

		}

		sortByShareInAsset(pensionYearStockList);
		return pensionYearStockList;
	}

	/*
	 * 자산내 비중 퍼센트를 기준으로 오름차순 정렬
	 */
	private void sortByShareInAsset(List<PensionYearStock> sortList) {
		Collections.sort(sortList, (investInfo1, investInfo2) -> {
			if (investInfo1.getChangeShareInAsset() < investInfo2.getChangeShareInAsset()) {
				return 1;
			} else if (investInfo1.getChangeShareInAsset() == investInfo2.getChangeShareInAsset()) {
				return 0;
			} else if (investInfo1.getChangeShareInAsset() > investInfo2.getChangeShareInAsset()) {
				return -1;
			}
			return 0;
		});
	}
}

