package catchcompany.web.module.pension.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestInfo {
	private String corporationName;
	private Double beforeShareInAsset;
	private Double currentShareInAsset;
	private String changeRate;
}
