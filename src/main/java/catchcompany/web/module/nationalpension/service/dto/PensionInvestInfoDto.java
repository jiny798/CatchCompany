package catchcompany.web.module.nationalpension.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class PensionInvestInfoDto {
	@JsonProperty("번호")
	private Long id;

	@JsonProperty("자산군 내 비중")
	private String shareInAsset;

	@JsonProperty("종목명")
	private String name;

	@JsonProperty("지분율(%)")
	private String shareRatio;

	@JsonProperty("평가액(억원)")
	private Long evaluation;
}
