package catchcompany.web.module.company.domain;

import catchcompany.web.module.company.infra.dto.CompanyDataDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_registration_code", unique = true)
	private String registrationCode;

	@Column(name = "streetName_address")
	private String streetNameAddress;

	@Column(name = "street_number_address")
	private String streetNumberAddress;

	@Column(name = "name")
	private String name;

	@Column(name = "business_type")
	private String businessType;

	@Column(name = "member_count")
	private String memberCount;

	@Column(name = "new_member_count")
	private String newMemberCount;

	@Column(name = "exit_member_count")
	private String exitMemberCount;

	@Column(name = "date")
	private String date;

	public static Company from(CompanyDataDto dto) {
		Company company = Company.builder()
			.registrationCode(dto.getCompanyRegistrationCode())
			.streetNameAddress(dto.getStreetNameAddress())
			.streetNumberAddress(dto.getStreetNumberAddress())
			.name(dto.getName())
			.businessType(dto.getBusinessType())
			.memberCount(dto.getMemberCount())
			.newMemberCount(dto.getNewMemberCount())
			.exitMemberCount(dto.getExitMemberCount())
			.date(dto.getDate())
			.build();

		return company;
	}
}
