package catchcompany.web.module.company.service.port;

import catchcompany.web.module.company.infra.dto.CompanyDataResponse;

public interface CompanyDataRestClient {
	public int getRepeatCount(String link);

	public CompanyDataResponse execute(String link, int page);
}
