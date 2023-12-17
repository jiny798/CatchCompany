package catchcompany.web.module.stock.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StockInfo {

	@JsonProperty("basDt")
	private String basDt; // 기준일자
	@JsonProperty("itmsNm")
	private String itmsNm; // 종목명
	@JsonProperty("mrktCtg")
	private String mrktCtg; // 코스닥,코스피
	@JsonProperty("clpr")
	private int clpr; // 종가
	@JsonProperty("fltRt")
	private double fltRt; // 등락률
	@JsonProperty("vs")
	private int vs; // 전일 대비 등락 ex 800, -1000
	@JsonProperty("mkp")
	private int mkp; // 시가
	@JsonProperty("mrktTotAmt")
	private Long mrktTotAmt; // 시가총액
	@JsonProperty("trqu")
	private Long trqu; // 거래량


}
