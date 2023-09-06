package com.shop.watershop.web;

import com.shop.watershop.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@GetMapping(value = {"/login","/"} )
	public String login() {
		return "login";
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());

		return "registration";
	}

	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model, HttpSession session) {

		if(bindingResult.hasErrors()){
			return "registration";
		}

		session.setAttribute("user", user);
		userService.save(user);

		return "redirect:/login?success";
	}

}
