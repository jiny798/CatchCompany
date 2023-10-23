package catchcompany.web.module.company.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InvestedCompany {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "corp_cls")
	private CorpClass corpClass;

	@Column(name = "corp_code")
	private String corpCode;

	@Column(name = "corp_name")
	private String corpName;

	@Column(name = "share_ratio")
	private int shareRatio;

	@Column(name = "initial_investment_date")
	private LocalDateTime initialInvestmentDate;

	@Column(name = "stock_count")
	private Long stockCount;

	@Column(name = "profit")
	private String profit;

}
