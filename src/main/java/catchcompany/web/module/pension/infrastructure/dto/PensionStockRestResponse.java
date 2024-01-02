package catchcompany.web.module.pension.infrastructure.dto;

import java.util.List;

import lombok.Getter;

// open data 에서 받아온 응답 DTO
@Getter
public class PensionStockRestResponse {
	public String currentCount;
	public List<PensionStockDto> data;
	public Long matchCount;
	public Long perPage;
	public Long totalCount;
}
