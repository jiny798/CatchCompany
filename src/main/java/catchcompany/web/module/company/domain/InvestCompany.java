package catchcompany.web.module.company.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class InvestCompany {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Company company;

	@Column(name = "corporation_cls")
	private CorporationClass corpClass;

	@Column(name = "corporation_code")
	private String CorporationCode;

	@Column(name = "corporation_name")
	private String name;

	@Column(name = "share_ratio")
	private int shareRatio;

	@Column(name = "initial_investment_date")
	private LocalDateTime initialInvestmentDate;

	@Column(name = "basic_stock_count")
	private String basicStockCount;

	@Column(name = "basic_stock_ratio")
	private String basicStockShareRatio;

	@Column(name = "basic_stock_evaluation_value")
	private String basicStockEvaluationValue;

	@Column(name = "current_stock_count")
	private String currentStockCount;

	@Column(name = "current_stock_share_ratio")
	private String currentStockShareRatio;

	@Column(name = "current_stock_evaluation_value")
	private String currentStockEvaluationValue;

	@Column(name = "recent_stock_amount_of_change")
	private String recentStockAmountOfChange;

	@Column(name = "recent_acquisition_amount")
	private String recentAcquisitionAmount;

	@Column(name = "recent_evaluation_gains_and_losses")
	private String recentEvaluationGainsAndLosses;


}
