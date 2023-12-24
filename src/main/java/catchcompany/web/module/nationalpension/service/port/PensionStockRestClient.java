package catchcompany.web.module.nationalpension.service.port;

import catchcompany.web.module.nationalpension.controller.dto.InvestYearInfo;
import catchcompany.web.module.nationalpension.service.admin.PensionStockRestResponse;

public interface PensionStockRestClient {
	public PensionStockRestResponse execute(InvestYearInfo investYearInfo);
}
