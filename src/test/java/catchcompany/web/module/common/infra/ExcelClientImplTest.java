package catchcompany.web.module.common.infra;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import catchcompany.web.module.corporation.domain.InvestOfCorporation;

class ExcelClientImplTest {
	@Test
	public void 액셀에서_원하는_객체로_가져올_수_있다() {
		ExcelClientImpl excelClient = new ExcelClientImpl();
		List<InvestOfCorporation> investList = excelClient.getRowList("src/main/resources/testCorpInvestInfo.xlsx",
			(rowList) -> {
				return convertRowsToInvest((List<String>)rowList);
			});
		assertThat(investList.size()).isEqualTo(9);
	}

	@Test
	public void 액셀에서_행범위를_지정하여_객체로_가져올_수_있다() {
		ExcelClientImpl excelClient = new ExcelClientImpl();
		List<InvestOfCorporation> investList = excelClient.getRowList("src/main/resources/testCorpInvestInfo.xlsx",
			1, 5,
			(rowList) -> {
				return convertRowsToInvest((List<String>)rowList);
			});
		assertThat(investList.size()).isEqualTo(5);
		assertThat(investList.get(0).getInvestorName()).isEqualTo("3S");
		assertThat(investList.get(0).getInvestTarget()).isEqualTo("단순투자");
		for(InvestOfCorporation invest : investList) {
			assertThat(invest.getInvestorName()).isNotEqualTo("회사명");
		}
	}

	private InvestOfCorporation convertRowsToInvest(List<String> rowList) {
		String investorName = rowList.get(0).trim(); // 투자하는 회사명
		String investCompany = rowList.get(4).trim(); // 투자받는 회사명
		String investTarget = rowList.get(6).trim(); // 투자목적
		return InvestOfCorporation.builder()
			.corporation(null)
			.investorName(investorName) // 투자하는 회사
			.corporationCode(rowList.get(1).trim())
			.name(investCompany) // 투자받는 회사
			.corporationClass("상장")
			.investTarget(investTarget)
			.initialInvestmentDate(rowList.get(5).trim())
			.currentStockCount(rowList.get(14).trim())  // 현재 주식 정보
			.currentStockShareRatio(rowList.get(15).trim())
			.currentStockEvaluationValue(rowList.get(16).trim())
			.recentStockAmountOfChange(rowList.get(11).trim()) // 최근 증감 정보
			.recentAcquisitionAmount(rowList.get(12).trim())
			.recentEvaluationGainsAndLosses(rowList.get(13).trim())
			.build();
	}
}