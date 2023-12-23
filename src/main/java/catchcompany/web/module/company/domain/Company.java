package catchcompany.web.module.company.domain;

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
public class Company {

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
	private List<Invest> invest = new ArrayList<>();

	public static Company createCompany(String corporationCode, String name, String stockCode) {
		Company company = new Company();
		company.corporationCode = corporationCode.trim();
		company.name = name.trim();
		company.stockCode = stockCode.trim();
		return company;
	}

	public String getCorporationCode() {
		return this.corporationCode;
	}
}
