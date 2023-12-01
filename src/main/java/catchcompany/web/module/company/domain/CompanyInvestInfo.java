package catchcompany.web.module.company.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CompanyInvestInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;

	private String investorName; // 투자하는 회사의 이름명

	@Column(name = "corporation_code")
	private String corporationCode; // 투자하는 회사의 코드

	@Column(name = "corporation_name")
	private String name; // 투자받는 회사명

	@Column(name = "initial_investment_date")
	private String initialInvestmentDate; // 최초 투자 날짜

	@Column(name = "basic_stock_count")
	private String basicStockCount; // 기초잔액 - 수량

	@Column(name = "basic_stock_ratio")
	private String basicStockShareRatio; // 기초잔액 - 지분율

	@Column(name = "basic_stock_evaluation_value")
	private String basicStockEvaluationValue; // 기초잔액 - 장부가액

	@Column(name = "current_stock_count")
	private String currentStockCount; // 기말 - 수량

	@Column(name = "current_stock_share_ratio")
	private String currentStockShareRatio; // 기말 - 지분율

	@Column(name = "current_stock_evaluation_value")
	private String currentStockEvaluationValue; // 기말 - 장부가액

	@Column(name = "recent_stock_amount_of_change")
	private String recentStockAmountOfChange; // 증감 - 취득 수량

	@Column(name = "recent_acquisition_amount")
	private String recentAcquisitionAmount; // 증감 - 취득(처분) 총금액

	@Column(name = "recent_evaluation_gains_and_losses")
	private String recentEvaluationGainsAndLosses; // 증감 - 평가손익

}
