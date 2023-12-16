package catchcompany.web.module.stock.domain;

public enum ItemType {
	BOOK("거래량으로 기업 찾기"), FOOD("x일 동안 상승하는 기업 찾기"), ETC("x일 동안 하락하는 기업 찾기");
	// @TODO 60, 120, 240 일선 아래,위 주식만 찾기,
	private final String description;

	ItemType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
