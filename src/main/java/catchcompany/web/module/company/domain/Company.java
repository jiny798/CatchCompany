package catchcompany.web.module.company.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "corporation_code")
	private String corporationCode;

	@Column(name = "corporation_name")
	private String name;

	@Column(name = "invest_company")
	@OneToMany
	private List<InvestCompany> investCompany = new ArrayList<>();

	public static Company createCompany(String corporationCode, String name) {
		Company company = new Company();
		company.corporationCode = corporationCode;
		company.name = name;
		return company;
	}

	public String getCorporationCode() {
		return this.corporationCode;
	}
}
