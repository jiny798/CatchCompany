package catchcompany.web.module.pension.service.port;

import catchcompany.web.module.pension.controller.dto.InvestYearInfo;
import catchcompany.web.module.pension.infrastructure.dto.PensionStockRestResponse;

public interface PensionStockRestClient {
	public PensionStockRestResponse execute(InvestYearInfo investYearInfo);
}
