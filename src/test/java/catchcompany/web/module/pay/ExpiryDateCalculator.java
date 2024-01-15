package catchcompany.web.module.pay;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

	public LocalDate calculateExpiryDate(PayData payData) {
		int addedMonths = payData.getPayAmount() == 100_000 ? 12 : payData.getPayAmount() / 10_000;

		if (payData.getFirstBillingDate() != null) { // 첫 납부일 존재할 경우
			LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonths);
			final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
			if (dayOfFirstBilling != candidateExp.getDayOfMonth()) {
				final int dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth();
				if (dayLenOfCandiMon < dayOfFirstBilling) {
					return candidateExp.withDayOfMonth(dayLenOfCandiMon);
				}
				return candidateExp.withDayOfMonth(dayOfFirstBilling);
			} else {
				return candidateExp;
			}
		} else { // 첫 납부일 존재하지 않을 때
			return payData.getBillingDate().plusMonths(addedMonths);
		}

	}
}
