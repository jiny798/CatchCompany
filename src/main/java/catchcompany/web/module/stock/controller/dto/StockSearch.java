package catchcompany.web.module.stock.controller.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockSearch {
	private List<String> marketList;
	private FirstCondition firstCondition;
	private String date;
	private Integer volume;
	public StockSearch(String date, int volume) {
		this.date = date;
		this.volume = volume;
	}
}
