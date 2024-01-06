package catchcompany.web.module.company.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.repository.CompanyJdbcRepository;
import catchcompany.web.module.company.infrastructure.dto.CompanyDataDto;
import catchcompany.web.module.company.infrastructure.dto.CompanyDataResponse;
import catchcompany.web.module.company.domain.repository.CompanyRepository;
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
		int repeatCount = companyDataRestClient.getTotalPageCount(link);
		IntStream stream = IntStream.range(1, repeatCount);
		stream.parallel().forEach(count -> {
			log.info("{} 시작 ---- ", count);
			CompanyDataResponse response = companyDataRestClient.execute(link, count);
			List<CompanyDataDto> companyList = response.getData();
			companyJdbcRepository.saveAll(companyList);
		});

	}


}
