package catchcompany.web.module.company.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.CompanyInvestInfo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@NoArgsConstructor
public class CompanyInfo {
	public String name;
	public Page<InvestInfoDto> pageForInvest; // 투자 목적 리스트
	public Page<InvestInfoDto> pageForBusiness; // 경영 참여 목적 리스트

	public CompanyInfo(Company company, Page<CompanyInvestInfo> pageForInvest,
		Page<CompanyInvestInfo> pageForBusiness) {
		this.name = company.getName();
		this.pageForInvest = pageForInvest.map(investInfo -> new InvestInfoDto(investInfo));
		this.pageForBusiness = pageForBusiness.map(investInfo -> new InvestInfoDto(investInfo));
	}

	@Data
	static class InvestInfoDto {
		public String investedCompany;

		public InvestInfoDto(CompanyInvestInfo companyInvestInfo) {
			investedCompany = companyInvestInfo.getName();
		}
	}
}

