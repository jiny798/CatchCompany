package catchcompany.web.module.company.exception;

public class NotExistCompany extends RuntimeException{
	public NotExistCompany() {
		super("존재하지 않는 회사입니다.");
	}
}
