package catchcompany.web.module.account.domain.entity;

public enum Role {
	ROLE_USER("유저"),
	ROLE_ADMIN("관리자");
	private String responseMessage;

	Role(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseMessage() {
		return this.responseMessage;
	}
}
