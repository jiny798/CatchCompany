package catchcompany.web.module.nationalpension.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class PensionInvestInfoDto {
	@JsonProperty("번호")
	public Long id;

	@JsonProperty("자산군 내 비중")
	public String shareOfAsset;

	@JsonProperty("종목명")
	public String name;

	@JsonProperty("지분율(%)")
	public String shareRatio;

	@JsonProperty("평가액(억원)")
	public Long valuation;
}
