package catchcompany.web.module.pay;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ExpiryDateCalculatorTest {

	@Test
	void 만원_납부시_한달_뒤가_만료일이_됨() {
		LocalDate billingDate = LocalDate.of(2019, 1, 31);
		int payAmount = 10_000;

		assertExpiryDate(billingDate, 10_000, LocalDate.of(2019, 2, 28));
	}

	private void assertExpiryDate(LocalDate billingDate, int payAmount, LocalDate expectedExpiryDate) {
		ExpiryDateCalculator calculator = new ExpiryDateCalculator();
		LocalDate realExpiryDate = calculator.calculateExpiryDate(billingDate, payAmount);
		assertEquals(expectedExpiryDate, realExpiryDate);
	}

}
