package catchcompany.web.module.account.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import catchcompany.web.module.account.controller.dto.SignUpForm;
import catchcompany.web.module.account.service.port.AccountRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

	private final AccountRepository accountRepository;

	@Override
	public boolean supports(Class<?> clazz) { // (3)
		return clazz.isAssignableFrom(SignUpForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SignUpForm signUpForm = (SignUpForm) target;
		if (accountRepository.existsByEmail(signUpForm.getEmail())) {
			errors.rejectValue("email", "invalid.email", new Object[]{signUpForm.getEmail()},
				"이미 사용중인 이메일입니다.");
		}
		if (accountRepository.existsByNickname(signUpForm.getNickname())) {
			errors.rejectValue("nickname", "invalid.nickname", new Object[]{signUpForm.getEmail()},
				"이미 사용중인 닉네임입니다.");
		}
		if (!signUpForm.getPassword().equals(signUpForm.getConfirmPassword())) {
			errors.rejectValue("password", "invalid.password", new Object[]{signUpForm.getEmail()},
				"비밀번호가 서로 일치하지 않습니다.");
		}

	}
}
