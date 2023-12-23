package catchcompany.web.module.company.controller.dto;

import org.springframework.data.domain.Page;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.Invest;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@NoArgsConstructor
public class CompanyInfo {
	public String name;
	public Page<InvestInfoDto> page; // 투자 목적 리스트

	public CompanyInfo(Company company, Page<Invest> page) {
		this.name = company.getName();
		this.page = page.map(investInfo -> new InvestInfoDto(investInfo));
	}

	@Data
	static class InvestInfoDto {
		public String investedCompany;
		public String currentStockCount;
		public String corporationClass;

		public InvestInfoDto(Invest invest) {
			investedCompany = invest.getName();
			currentStockCount = invest.getCurrentStockCount();
			corporationClass = invest.getCorporationClass();
		}
	}
}

