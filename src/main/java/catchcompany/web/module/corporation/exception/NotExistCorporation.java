package catchcompany.web.module.corporation.exception;

public class NotExistCorporation extends RuntimeException{
	public NotExistCorporation() {
		super("존재하지 않는 회사입니다.");
	}
}
