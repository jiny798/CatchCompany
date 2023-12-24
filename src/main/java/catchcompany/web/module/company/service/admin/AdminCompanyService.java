package catchcompany.web.module.company.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.Invest;
import catchcompany.web.module.company.service.admin.CompanyDataProcessor;
import catchcompany.web.module.company.service.admin.InvestDataProcessor;
import catchcompany.web.module.company.service.port.InvestRepository;
import catchcompany.web.module.company.service.port.CompanyRepository;
import catchcompany.web.module.uri.application.UriManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCompanyService {
	private final UriManager uriManager;
	private final CompanyRepository companyRepository;
	private final InvestRepository investRepository;
	private final CompanyDataProcessor companyDataProcessor;
	private final InvestDataProcessor investDataProcessor;

	public void processCompanyListToDatabase() {
		List<Company> companyList = companyDataProcessor.getCompanyList();
		companyList.stream().parallel().forEach(i -> {
			companyRepository.save(i);
		});
	}


	public void processCompanyInvestInfoToDatabase(String path) {
		List<Invest> investList = investDataProcessor.getInvestList(path);
		investList.stream().parallel()
			.forEach(invest -> {
				investRepository.save(invest);
			});
	}

}
