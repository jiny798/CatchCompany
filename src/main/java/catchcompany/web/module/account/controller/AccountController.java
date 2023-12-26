package catchcompany.web.module.account.controller;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.account.controller.dto.SignUpForm;
import catchcompany.web.module.account.controller.validator.SignUpFormValidator;
import catchcompany.web.module.account.domain.entity.AccountCreate;
import catchcompany.web.module.account.service.AccountService;
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
	public String signUpSubmit(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors) {
		if (errors.hasErrors()) {
			return "account/register";
		}
		accountService.signUp(signUpForm);
		return "redirect:/";
	}

}
