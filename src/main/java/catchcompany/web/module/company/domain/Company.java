package catchcompany.web.module.company.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "corp_code")
	private String corpCode;

	@Column(name = "corp_name")
	private String name;

	@OneToOne
	private InvestedCompany investedCompany;

}
