package catchcompany.web.module.corporation.controller.dto;

import org.springframework.data.domain.Page;

import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.domain.InvestOfCorporation;
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

	public CompanyInfo(Corporation corporation, Page<InvestOfCorporation> page) {
		this.name = corporation.getName();
		this.page = page.map(investInfo -> new InvestInfoDto(investInfo));
	}

	@Data
	static class InvestInfoDto {
		public String investedCompany;
		public String currentStockCount;
		public String corporationClass;

		public InvestInfoDto(InvestOfCorporation invest) {
			investedCompany = invest.getName();
			currentStockCount = invest.getCurrentStockCount();
			corporationClass = invest.getCorporationClass();
		}
	}
}

