package catchcompany.web.module.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import catchcompany.web.module.account.controller.form.SignUpForm;
import catchcompany.web.module.account.controller.validator.SignUpFormValidator;
import catchcompany.web.module.account.domain.entity.Account;
import catchcompany.web.module.account.infra.repository.AccountJpaRepository;
import catchcompany.web.module.account.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/account")
@RequiredArgsConstructor
@Controller
@Slf4j
public class AccountController {
	private final SignUpFormValidator signUpFormValidator;
	private final AccountService accountService;
	private final AccountJpaRepository accountRepository;

	@InitBinder("signUpForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(signUpFormValidator);
	}

	@GetMapping("/sign-up")
	public String signUpForm(Model model) {
		model.addAttribute("signUpForm", new SignUpForm());
		return "account/register";
	}

	@PostMapping("/sign-up")
	public String signUpSubmit(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors,
		RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()) {
			return "account/register";
		}
		accountService.signUp(signUpForm);
		redirectAttributes.addFlashAttribute("email", signUpForm.getEmail());
		return "redirect:/account/email-auth-send";
	}

	@GetMapping("/check-email-token")
	public String verifyEmailAuth(String token, String email, Model model, HttpServletRequest request,
		HttpServletResponse response) {
		Account account = accountRepository.findByEmailAndIsValid(email, false).orElse(null);
		if (account == null) {
			model.addAttribute("error", "wrong.email");
			return "account/email-auth/email-verification";
		}
		if (!account.getEmailAuthToken().equals(token)) {
			model.addAttribute("error", "wrong.token");
			return "account/email-auth/email-verification";
		}
		accountService.certificate(account, token);
		accountService.login(account, request, response);
		model.addAttribute("nickname", account.getNickname());
		return "account/email-auth/email-verification";
	}

	@GetMapping("/email-auth-send")
	public String sendEmailAuth() {
		return "account/email-auth/send";
	}

}
