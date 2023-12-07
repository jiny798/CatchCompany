package catchcompany.web.module.nationalpension.service.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class PensionInvestResult {
	public String currentCount;
	public List<PensionInvestInfoDto> data;
	public Long matchCount;
	public Long perPage;
	public Long totalCount;
}
