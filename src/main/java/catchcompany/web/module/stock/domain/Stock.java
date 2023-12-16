package catchcompany.web.module.stock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity @Getter
public class Stock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String corporationName; // 종목명

	private String mrktCtg; // 시장 구분

	private int mkp; // 시가

	private int dpr; // 종가

	private double fltRt; // 등락률

	private int vs; // 전일 대비 등락

	private Long mrktTotAmt; // 시가총액

	private Long trqu; // 거래량

	private String status; // 상승,하락

}
