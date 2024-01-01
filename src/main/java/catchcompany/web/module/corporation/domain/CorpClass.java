package catchcompany.web.module.corporation.domain;

public enum CorpClass { // classification
	Y("유가"), K("코스닥"), N("코넥스"), E("기타");

	private String type;

	CorpClass(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public static CorpClass createCorpClass(String type) {
		if(type.equals("Y")){
			return CorpClass.Y;
		}
		if(type.equals("K")){
			return CorpClass.K;
		}
		if(type.equals("N")){
			return CorpClass.N;
		}
		if(type.equals("E")){
			return CorpClass.E;
		}
		return null;
	}
}
