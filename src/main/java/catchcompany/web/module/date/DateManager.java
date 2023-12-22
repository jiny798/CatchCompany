package catchcompany.web.module.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import catchcompany.web.module.stock.domain.Stock;
import catchcompany.web.module.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DateManager {
	public static int today = 20231218;
	private final StockRepository stockRepository;

	public String calculatePreviousDateOf(String date) {
		LocalDate inputDate = LocalDate.of(Integer.parseInt(date.substring(0, 4)),
			Integer.parseInt(date.substring(4, 6)),
			Integer.parseInt(date.substring(6, 8)));
		LocalDate yesterdayDate = inputDate.minusDays(1);
		while (true) {
			List<Stock> list = stockRepository.findByBasDt(
				yesterdayDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			if (!list.isEmpty()) { // 전날 주식정보가 있으면 빠져나온다
				break;
			} else {
				yesterdayDate = yesterdayDate.minusDays(1); // 주말이거나 쉬는날이면 하루 더 빼서 체크
			}
		}
		return yesterdayDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	}
}
