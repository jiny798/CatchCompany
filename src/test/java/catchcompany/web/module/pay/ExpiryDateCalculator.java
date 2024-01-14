package catchcompany.web.module.pay;

import java.time.LocalDate;

public class ExpiryDateCalculator {

	public LocalDate calculateExpiryDate(LocalDate billingDate, int payAmount) {
		return billingDate.plusMonths(1);
	}
}
