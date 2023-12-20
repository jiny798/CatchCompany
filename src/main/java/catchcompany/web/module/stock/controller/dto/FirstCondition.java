package catchcompany.web.module.stock.controller.dto;

public enum FirstCondition {
	TRADING_VOLUME("거래량으로 기업 찾기");
	// @TODO 60, 120, 240 일선 아래,위 주식만 찾기,
	private final String description;

	FirstCondition(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
