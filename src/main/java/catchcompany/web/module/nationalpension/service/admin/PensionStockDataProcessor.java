package catchcompany.web.module.nationalpension.service.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import catchcompany.web.module.nationalpension.controller.dto.InvestYearInfo;
import catchcompany.web.module.nationalpension.domain.PensionStock;
import catchcompany.web.module.nationalpension.infrastructure.PensionInvestRepository;
import catchcompany.web.module.nationalpension.service.port.PensionStockRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PensionStockDataProcessor {

	private final PensionInvestRepository pensionInvestRepository;
	private final PensionStockRestClient pensionStockRestClient;


	public void saveInvestInfo(InvestYearInfo investYearInfo) {

		PensionStockRestResponse pensionStockRestResponse = pensionStockRestClient.execute(investYearInfo);
		List<PensionStockDto> list = pensionStockRestResponse.getData();
		for (PensionStockDto dto : list) {
			PensionStock info = PensionStock.builder()
				.corporationName(dto.getName())
				.evaluation(dto.getEvaluation())
				.currentShareInAsset(dto.getShareInAsset())
				.shareRatio(dto.getShareRatio())
				.year(investYearInfo.getYear())
				.build();
			pensionInvestRepository.save(info);
		}
	}

	public void sortInvestInfo(int year) {
		List<PensionStock> investList = pensionInvestRepository.findByYear(year);
		List<PensionStock> sortList = new ArrayList<>();
		for (PensionStock stock : investList) {
			// year 년도 회사이름 가져오기
			String corpName = stock.getCorporationName();
			// 이전년도 회사정보 가져오기
			List<PensionStock> findList = pensionInvestRepository.findByYearAndCorporationName(year - 1, corpName);
			if (findList.isEmpty()) {
				// 없어진 회사는 조회하지 않는다.
				continue;
			} else {
				PensionStock beforeYearStock = findList.get(0);
				PensionStock sortInvest = PensionStock.builder()
					.corporationName(stock.getCorporationName())
					.evaluation(stock.getEvaluation()) // 이전년도 평가액 차이를 구한다
					.changeShareInAsset(stock.getCurrentShareInAsset() - beforeYearStock.getCurrentShareInAsset())
					.beforeShareInAsset(beforeYearStock.getCurrentShareInAsset())
					.currentShareInAsset(stock.getCurrentShareInAsset())
					.shareRatio(stock.getShareRatio())
					.year(year)
					.build();
				sortList.add(sortInvest); // 이전년도 평가액 차이 구한 회사만 list 추가
			}
		}

		for (PensionStock stock : investList) {
			pensionInvestRepository.delete(stock);
		}

		sortByShareInAsset(sortList);
		for (PensionStock sortInvest : sortList) {
			pensionInvestRepository.save(sortInvest);
		}
	}

	/*
	* 자산내 비중 퍼센트를 기준으로 오름차순 정렬
	 */
	private void sortByShareInAsset(List<PensionStock> sortList) {
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

