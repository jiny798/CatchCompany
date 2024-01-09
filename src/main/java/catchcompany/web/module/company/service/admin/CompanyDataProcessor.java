package catchcompany.web.module.company.service.admin;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import catchcompany.web.module.company.infra.repository.CompanyJdbcRepository;
import catchcompany.web.module.company.infra.dto.CompanyDataDto;
import catchcompany.web.module.company.infra.dto.CompanyDataResponse;
import catchcompany.web.module.company.service.port.CompanyDataRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompanyDataProcessor {
	private final CompanyDataRestClient companyDataRestClient;
	private final CompanyJdbcRepository companyJdbcRepository;

	public void processSaveCompanyInfo(String link) {
		int repeatCount = companyDataRestClient.getRepeatCount(link);
		IntStream stream = IntStream.rangeClosed(1, repeatCount);
		stream.parallel().forEach(page -> {
			CompanyDataResponse response = companyDataRestClient.execute(link, page);
			List<CompanyDataDto> companyList = response.getData();
			companyJdbcRepository.saveAll(companyList);
		});

	}


}
