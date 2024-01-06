package catchcompany.web.module.company.infrastructure.dto;

import java.util.List;

import lombok.Data;

@Data
public class CompanyDataResponse {
	public String currentCount;
	public List<CompanyDataDto> data;
	public int matchCount;
	public int perPage;
	public int totalCount;
	public int page;
}
