package catchcompany.web.module.company.service;

import java.io.FileInputStream;
import java.util.List;

import catchcompany.web.module.company.domain.CompanyInvestInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompanyInvestDataProcessor {
	private final FileInputStream fileInputStream;

	public List<List<String>> getRecordInfoList() {
		return null;
	}

	public List<CompanyInvestInfo> getInvestInfoList(){
		return null;
	}
}
