package catchcompany.web.module.company.domain;

public enum CorpClass { // classification
	Y("유가"), K("코스닥"), N("코넥스"), E("기타");

	private String type;

	CorpClass(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
