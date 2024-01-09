package catchcompany.web.module.company.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class CompanyDataDto {
	@JsonProperty("가입자수")
	private String memberCount;

	@JsonProperty("사업자등록번호")
	private String companyRegistrationCode;

	@JsonProperty("사업장도로명상세주소")
	private String streetNameAddress;

	@JsonProperty("사업장지번상세주소")
	private String streetNumberAddress;

	@JsonProperty("사업장명")
	private String name;

	@JsonProperty("사업장업종코드명")
	private String businessType;

	@JsonProperty("신규취득자수")
	private String newMemberCount;

	@JsonProperty("상실가입자수")
	private String exitMemberCount;

	@JsonProperty("자료생성년월")
	private String date;


}
