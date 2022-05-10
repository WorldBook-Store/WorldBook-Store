package com.java.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.java.entity.Author;
import com.java.entity.Customer;
import com.java.repository.AuthorRepositoy;
import com.java.repository.CustomersRepository;

@Controller
public class CustomerController {

	@Value("./")
	private String pathUploadImage;

	@Autowired
	CustomersRepository customersRepository;

	// show Customer
	@GetMapping(value = "/admin/customerList")
	public String authorList(Model model) {

//		List<Customer> listCustomer = customersRepository.findAll();
		List<Customer> listCustomer = getCustomers();
		model.addAttribute("listCustomer", listCustomer);
		model.addAttribute("customer", new Customer());

		return "admin/customerList";
	}

	//Xoa acc admin
	private List<Customer> getCustomers() {
		List<Customer> listCustomer = new ArrayList<Customer>();
		listCustomer = customersRepository.findAll();
		
		listCustomer.removeIf(t -> t.getCustomerId().equals("admin"));
		
		return listCustomer;
	}

	
//	
//	@Autowired
//	AuthorRepositoy authorRepositoy;

	// Edit author			   editCustomer
	@GetMapping(value ="/admin/editCustomer")
	public String editCustomer(@RequestParam(required=false,name="customerId") String customerId, Model model) {
		
		Customer customer = new Customer();
		
		customer = customersRepository.findCustomersLogin(customerId);
		
		model.addAttribute("customer", customer);
		return "admin/editCustomer";
	}

	// Edit author				 doEditCustomer  customerId
	@PostMapping(value = "/admin/doEditCustomer/{customerId}")
	public String editCustomer(@PathVariable(name="customerId") String customerId, @ModelAttribute("customer") Customer customer, Model model) {

		customer.setCustomerId(customerId);
//		customersRepository.updateCustomer(customer.getEmail(), customer.getFullname(), customerId);
		return "redirect:/admin/customerList";
	}

//	// add author
//	@PostMapping(value = "/addAuthor")
//	public String addAuthor(Model model, @Valid @ModelAttribute("author") Author author, BindingResult result,
//			@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {
//		try {
//			File convFile = new File(pathUploadImage + "/" + file.getOriginalFilename());
//			FileOutputStream fos = new FileOutputStream(convFile);
//			fos.write(file.getBytes());
//			fos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		author.setAuthorImage(file.getOriginalFilename());
//		Author b = authorRepositoy.save(author);
//		if (null != b) {
//			model.addAttribute("message", "Thêm mới thành công!");
//			model.addAttribute("book", author);
//		} else {
//			model.addAttribute("message", "Thêm mới thất bại!");
//			model.addAttribute("book", author);
//		}
//		model.addAttribute("message", "Thêm mới thành công!");
//
//		return "redirect:/admin/authorList";
//	}
//
	// delete customer
	@GetMapping("/deleteCustomer/{id}")
	public String deleteAuthor(@PathVariable("customerId") String customerId, Model model) {

		customersRepository.deleteById(customerId);
		return "redirect:/admin/authorList";
	}
}
