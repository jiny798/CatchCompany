package catchcompany.web.module.nationalpension.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import catchcompany.web.module.nationalpension.repository.PensionInvestInfoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PensionApiService {

	private final PensionInvestInfoRepository pensionInvestInfoRepository;

	@Value("${open-data.auth-key}")
	private String authKey;

	@Value("${open-data.api-key}")
	private String apiKey;

	public void processInvestInfoSave() {

	}
}
