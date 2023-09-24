package catchcompany.web.module.company.domain;

public enum CorpCls { // classification
	Y("유가"), K("코스닥"), N("코넥스"), E("기타");

	private String type;

	CorpCls(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
