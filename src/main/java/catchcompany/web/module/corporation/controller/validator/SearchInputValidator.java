package catchcompany.web.module.corporation.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import catchcompany.web.module.corporation.infra.repository.CorporationJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SearchInputValidator implements Validator {

	private final CorporationJpaRepository corporationRepository;

	@Override
	public boolean supports(Class<?> clazz) { // (3)
		return clazz.isAssignableFrom(String.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		String company = (String) target;
		if (!corporationRepository.existsByName(company)) {
			errors.rejectValue("name", "invalid.name", new Object[]{company},
				"존재하지 않는 회사명입니다.");
		}

	}
}
