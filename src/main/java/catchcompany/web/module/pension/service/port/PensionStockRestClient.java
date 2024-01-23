package catchcompany.web.module.pension.service.port;

import catchcompany.web.module.pension.controller.dto.RequestYearInfo;
import catchcompany.web.module.pension.infra.dto.PensionStockRestResponse;

public interface PensionStockRestClient {
	public PensionStockRestResponse execute(RequestYearInfo requestYearInfo);
}
