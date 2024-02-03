package catchcompany.web.module.corporation.controller.dto.response;

import org.springframework.data.domain.Page;

import catchcompany.web.module.common.dto.PageInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@NoArgsConstructor
public class InvestInfoResponse {
	private String name;
	private String type;
	private Page<CorporationInvestInfo> investInfoList; // 투자 목적 리스트
	private PageInfo pageInfo;

	public InvestInfoResponse(String name, String type, Page<CorporationInvestInfo> page, PageInfo pageInfo) {
		this.name = name;
		this.type = type;
		this.investInfoList = page;
		this.pageInfo = pageInfo;
	}

}

