package catchcompany.web.module.stock.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockSearchResult {
	private String corpName;
	private Long yesterdayTradingVolume;
	private Long todayTradingVolume;
}
