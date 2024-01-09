package catchcompany.web.module.company.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import catchcompany.web.module.company.infra.dto.CompanyDataDto;

class CompanyTest {

	@Test
	public void CompanyDataDto_객체로_생성할_수_있다() {
		//given
		CompanyDataDto companyData = new CompanyDataDto(
			"123",
			"123456",
			"강남구 대치동",
			"1505",
			"차카오",
			"인터넷",
			"12312",
			"12345",
			"20240109"
		);
		//when
		Company company = Company.from(companyData);
		//then
		assertThat(company.getId()).isNull();
		assertThat(company.getMemberCount()).isEqualTo("123");
		assertThat(company.getRegistrationCode()).isEqualTo("123456");
		assertThat(company.getStreetNameAddress()).isEqualTo("강남구 대치동");
		assertThat(company.getStreetNumberAddress()).isEqualTo("1505");
		assertThat(company.getName()).isEqualTo("차카오");
		assertThat(company.getBusinessType()).isEqualTo("인터넷");
		assertThat(company.getNewMemberCount()).isEqualTo("12312");
		assertThat(company.getExitMemberCount()).isEqualTo("12345");
		assertThat(company.getDate()).isEqualTo("20240109");
	}
}