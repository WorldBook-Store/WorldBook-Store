package com.java.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.java.entity.Customer;
import com.java.error.UserAlreadyExistException;
import com.java.repository.CustomersRepository;
//import com.java.service.IUserService;
import com.java.service.OnRegistrationCompleteEvent;

@Controller
public class RegisterController extends CommomController {

	@Autowired
	private ApplicationEventPublisher eventPublisher;


	@Autowired
	CustomersRepository customersRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/registered")
	public String showRegistrationForm(final HttpServletRequest request, final Model model) {
		final Customer accountDto = new Customer();
		model.addAttribute("user", accountDto);
		return "site/register";
	}

	@PostMapping("/registered")
	public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid final Customer customer,
			final HttpServletRequest request, final Errors errors) {

		try {

			customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
			final Customer registered = customersRepository.save(customer);

			final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
		} catch (final UserAlreadyExistException uaeEx) {
			ModelAndView mav = new ModelAndView("site/register", "user", customer);
			return mav;
		} catch (final RuntimeException ex) {
			return new ModelAndView("emailError", "user", customer);
		}
		return new ModelAndView("site/successRegister", "user", customer);
	}

	@GetMapping("/registrationConfirm")
	public String confirmRegistration(final HttpServletRequest request){
		return "site/home";
	}

	@ModelAttribute(value = "customer")
	public Customer initCustomer(Principal principal) {
		Customer customer = new Customer();
		if (principal != null) {
			customer = (Customer) ((Authentication) principal).getPrincipal();
		}
		return customer;
	}

	// check email
	public boolean checkEmail(String email) {
		List<Customer> list = customersRepository.findAll();
		for (Customer c : list) {
			if (c.getEmail().equalsIgnoreCase(email)) {
				return false;
			}
		}
		return true;
	}

	// check ID Login
	public boolean checkIdlogin(String customerId) {
		List<Customer> listC = customersRepository.findAll();
		for (Customer c : listC) {
			if (c.getCustomerId().equalsIgnoreCase(customerId)) {
				return false;
			}
		}
		return true;
	}
}
