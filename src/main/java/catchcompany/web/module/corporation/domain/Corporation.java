package catchcompany.web.module.corporation.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity @Getter
public class Corporation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "corporation_code")
	private String corporationCode;

	@Column(name = "corporation_name")
	private String name;

	@Column(name = "stock_code")
	private String stockCode;

	@Column(name = "invest_company")
	@OneToMany(mappedBy = "company")
	private List<InvestOfCorporation> invest = new ArrayList<>();

	public static Corporation createCompany(String corporationCode, String name, String stockCode) {
		Corporation corporation = new Corporation();
		corporation.corporationCode = corporationCode.trim();
		corporation.name = name.trim();
		corporation.stockCode = stockCode.trim();
		return corporation;
	}

	public String getCorporationCode() {
		return this.corporationCode;
	}
}
