package com.shop.watershop.controllers;

import com.shop.watershop.models.User;
import com.shop.watershop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
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
	public String registerUserAccount( @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
		if(userService.findAll().stream()
				.anyMatch(user1 -> user1.getEmail().equals(user.getEmail()))){
			bindingResult.rejectValue("email","", "Email already registered");
		}
			if(bindingResult.hasErrors()){
			return "registration";
		}

		userService.save(user);

		return "redirect:/login?success";
	}

}
