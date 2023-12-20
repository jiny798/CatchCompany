package catchcompany.web.module.stock.exception;

public class AlreadyProcessedStockDateException extends RuntimeException{
	public AlreadyProcessedStockDateException(String msg) {
		super(msg);
	}
}
