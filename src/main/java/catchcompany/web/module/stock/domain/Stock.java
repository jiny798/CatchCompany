package catchcompany.web.module.stock.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity @Getter
public class Stock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String basDt; // 기준일자

	private String srtnCd; // 유일성 보장 코드

	private String isinCd; // 국제 채권 식별 번호.

	private String itmsNm; // 종목명

	private String mrktCtg; // 코스닥,코스피

	private int clpr; // 종가

	private int vs; // 전일 대비 등락 ex 800, -1000

	private double fltRt; // 등락률

	private int mkp; // 시가

	private int hipr; // 고가

	private int lopr; // 저가

	private Long trqu; // 거래량

	private Long trPrc; // 거래대금

	private Long lstgStCnt; // 상장 주식수

	private Long mrktTotAmt; // 시가총액

	private boolean status; // 상승

}
