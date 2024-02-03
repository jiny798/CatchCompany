package catchcompany.web.module.pension.controller.dto.response;

import org.springframework.data.domain.Page;

import catchcompany.web.module.common.dto.PageInfo;
import lombok.Getter;

@Getter
public class QuarterInvestResponse {

	private Page<QuarterInvestInfo> investInfoList;
	private PageInfo pageInfo;

	public QuarterInvestResponse(Page<QuarterInvestInfo> page, PageInfo pageInfo) {
		this.pageInfo = pageInfo;
		this.investInfoList = page;
	}
}
