package catchcompany.web.module.stock.controller.validation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import catchcompany.web.module.stock.controller.dto.StockSearch;
import catchcompany.web.module.stock.domain.Stock;
import catchcompany.web.module.stock.infra.repository.StockJpaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StockSearchValidator implements Validator {

	private final StockJpaRepository stockJpaRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return StockSearch.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		StockSearch stockSearch = (StockSearch)target;
		if (stockSearch.getVolume() == null) {
			((BindingResult)errors).addError(new FieldError("stockSearch", "volume", "거래량은 필수입니다."));
		}
		List<Stock> stockList = stockJpaRepository.findByBasDt(stockSearch.getDate());
		if (!StringUtils.hasText(stockSearch.getDate()) || stockList.isEmpty()) {
			((BindingResult)errors).addError(new FieldError("stockSearch", "date", "해당 날짜에 업데이트된 시세 정보가 없습니다."));
		}

	}
}
