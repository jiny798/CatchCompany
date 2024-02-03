package catchcompany.web.module.pension.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuarterInvestInfo {
	private String corporationName;
	private Double beforeShareRatio;
	private Double currentShareRatio;
	private String changeRate;
}
