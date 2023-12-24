package catchcompany.web.module.nationalpension.service.admin;

import java.util.List;

import lombok.Getter;

@Getter
public class PensionStockRestResponse {
	public String currentCount;
	public List<PensionStockDto> data;
	public Long matchCount;
	public Long perPage;
	public Long totalCount;
}
