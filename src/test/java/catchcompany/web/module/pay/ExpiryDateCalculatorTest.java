package catchcompany.web.module.pay;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ExpiryDateCalculatorTest {

	@Test
	void 만원_납부시_한달_뒤가_만료일이_됨() {
		assertExpiryDate(
			PayData.builder()
				.billingDate(LocalDate.of(2019, 3, 1))
				.payAmount(10_000)
				.build(),
			LocalDate.of(2019, 4, 1));
	}

	@Test
	void 납부일의_한달_뒤_일자가_다를경우() {
		assertExpiryDate(
			PayData.builder()
				.billingDate(LocalDate.of(2019, 1, 31))
				.payAmount(10_000)
				.build(),
			LocalDate.of(2019, 2, 28));
	}

	private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
		ExpiryDateCalculator calculator = new ExpiryDateCalculator();
		LocalDate realExpiryDate = calculator.calculateExpiryDate(payData);
		assertEquals(expectedExpiryDate, realExpiryDate);
	}

}
