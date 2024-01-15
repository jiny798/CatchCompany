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

	@Test
	void 연속_3개월_납부_시_첫_납부일을_기준으로_만료일을_계산() {
		PayData payData = PayData.builder()
			.firstBillingDate(LocalDate.of(2019, 1, 31))
			.billingDate(LocalDate.of(2019, 2, 28))
			.payAmount(10_000)
			.build();
		assertExpiryDate(payData, LocalDate.of(2019, 3, 31));

		PayData payData2 = PayData.builder()
			.firstBillingDate(LocalDate.of(2019, 1, 30))
			.billingDate(LocalDate.of(2019, 2, 28))
			.payAmount(10_000)
			.build();
		assertExpiryDate(payData2, LocalDate.of(2019, 3, 30));

		PayData payData3 = PayData.builder()
			.firstBillingDate(LocalDate.of(2019, 5, 31))
			.billingDate(LocalDate.of(2019, 6, 30))
			.payAmount(10_000)
			.build();
		assertExpiryDate(payData3, LocalDate.of(2019, 7, 31));

	}

	private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
		ExpiryDateCalculator calculator = new ExpiryDateCalculator();
		LocalDate realExpiryDate = calculator.calculateExpiryDate(payData);
		assertEquals(expectedExpiryDate, realExpiryDate);
	}

}
