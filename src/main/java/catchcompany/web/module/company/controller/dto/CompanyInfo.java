package catchcompany.web.module.company.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.CompanyInvestInfo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter @Slf4j
@NoArgsConstructor
public class CompanyInfo {
	public String name;
	public List<InvestInfo> investList;

	public CompanyInfo(Company company) {
		this.name = company.getName();
		this.investList = company.getCompanyInvestInfo().stream()
			.map(companyInvestInfo -> new InvestInfo(companyInvestInfo))
			.collect(Collectors.toList());

	}

	@Data
	static class InvestInfo {
		public String investedCompany;

		public InvestInfo(CompanyInvestInfo companyInvestInfo) {
			investedCompany = companyInvestInfo.getName();
		}
	}
}

