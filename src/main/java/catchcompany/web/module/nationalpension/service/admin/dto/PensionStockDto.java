package catchcompany.web.module.nationalpension.service.admin.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class PensionStockDto {
	@JsonProperty("번호")
	private Long id;

	@JsonAlias({"자산군 내 비중", "자산군 내 비중(%)", "자산군 내 비중(퍼센트)"})
	private Double shareInAsset;

	@JsonProperty("종목명")
	private String name;

	@JsonAlias({"지분율(%)", "지분율(퍼센트)"})
	private String shareRatio;

	@JsonAlias({"평가액(억원)", "평가액(억 원)"})
	private Long evaluation;
}
