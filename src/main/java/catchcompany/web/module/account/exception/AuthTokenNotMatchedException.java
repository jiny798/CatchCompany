package catchcompany.web.module.account.exception;

public class AuthTokenNotMatchedException extends RuntimeException {
	public AuthTokenNotMatchedException() {
		super("인증 토큰이 일치하지 않습니다");
	}

	public AuthTokenNotMatchedException(String msg) {
		super(msg);
	}
}
