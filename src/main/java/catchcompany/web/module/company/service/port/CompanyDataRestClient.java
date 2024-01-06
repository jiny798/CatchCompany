package catchcompany.web.module.company.service.port;

import catchcompany.web.module.company.infrastructure.dto.CompanyDataResponse;

public interface CompanyDataRestClient {
	public int getTotalPageCount(String link);

	public CompanyDataResponse execute(String link, int page);
}
