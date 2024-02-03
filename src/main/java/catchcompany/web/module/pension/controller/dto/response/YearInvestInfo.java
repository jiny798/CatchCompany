package catchcompany.web.module.pension.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class YearInvestInfo {
	private String corporationName;
	private Double beforeShareInAsset;
	private Double currentShareInAsset;
	private String changeRate;
}
