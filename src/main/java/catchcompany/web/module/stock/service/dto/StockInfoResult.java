package catchcompany.web.module.stock.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StockInfoResult {
	@JsonProperty("header.resultCode")
	private String resultCode;
	// @JsonProperty("item")
	// private List<StockInfo> list;
}
