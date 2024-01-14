package catchcompany.web.module.pay;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayData {

	private LocalDate firstBillingDate;
	private LocalDate billingDate;
	private int payAmount;

}
