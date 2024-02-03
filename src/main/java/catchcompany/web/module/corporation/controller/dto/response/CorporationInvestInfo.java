package catchcompany.web.module.corporation.controller.dto.response;

import catchcompany.web.module.corporation.domain.InvestOfCorporation;

public class CorporationInvestInfo {
	public String investedCompany;
	public String currentStockCount;
	public String corporationClass;

	public CorporationInvestInfo(InvestOfCorporation invest) {
		investedCompany = invest.getName();
		currentStockCount = invest.getCurrentStockCount();
		corporationClass = invest.getCorporationClass();
	}
}
