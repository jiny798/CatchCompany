package catchcompany.web.module.company.domain;

public enum CorporationClass { // classification
	Y("유가"), K("코스닥"), N("코넥스"), E("기타");

	private String type;

	CorporationClass(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public static CorporationClass createCorpClass(String type) {
		if(type.equals("Y")){
			return CorporationClass.Y;
		}
		if(type.equals("K")){
			return CorporationClass.K;
		}
		if(type.equals("N")){
			return CorporationClass.N;
		}
		if(type.equals("E")){
			return CorporationClass.E;
		}
		return null;
	}
}
