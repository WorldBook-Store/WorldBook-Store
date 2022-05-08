package com.java.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.entity.Author;
import com.java.entity.Book;
import com.java.entity.Category;
import com.java.entity.Companie;
import com.java.entity.Order;
import com.java.repository.AuthorRepositoy;
import com.java.repository.BookRepository;
import com.java.repository.CategoryRepository;
import com.java.repository.CompanieRepository;
import com.java.repository.OrderRepository;

@Controller
public class IndexController {

	@Value("./")
	private String pathUploadImage;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	AuthorRepositoy authorRepositoy;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	CompanieRepository companieRepository;

	@GetMapping(value = "/admin/home")
	public String home() {

		return "admin/index";
	}

	// show order
	@GetMapping(value = "/admin/orders")
	public String orders(Model model) {

		List<Order> listOrders = orderRepository.findAll();
		model.addAttribute("listOrders", listOrders);
		model.addAttribute("order", new Order());

		return "admin/orderList";
	}

	// show books
	@GetMapping(value = "/admin/books")
	public String books(Model model) {

		List<Book> listBooks = bookRepository.findAll();
		model.addAttribute("listBooks", listBooks);

		model.addAttribute("book", new Book());

		return "admin/bookList";
	}

	// Hiển thị list company select
	@ModelAttribute("companyList")
	public List<Companie> companyList(Model model) {
		List<Companie> companyList = companieRepository.findAll();
		model.addAttribute("companyList", companyList);
		return companyList;
	}

	// Hiển thị list category select
	@ModelAttribute("categoryList")
	public List<Category> categories(Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categoryList", categoryList);
		return categoryList;
	}

	// Hiển thị list author select
	@ModelAttribute("authorList")
	public List<Author> authors(Model model) {
		List<Author> authorList = authorRepositoy.findAll();
		model.addAttribute("authorList", authorList);
		return authorList;
	}

	// update author
	@PostMapping("/update/{id}")
	public String updateAuthor(@PathVariable("id") Integer id, @Valid Author author, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			author.setId(id);
			return "update-author";
		}

		authorRepositoy.save(author);
		return "redirect:/admin/authorList";
	}

	// add product
	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("book") Book book, ModelMap model,
			@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {
		try {
			File convFile = new File(pathUploadImage + "/" + file.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		book.setBookImage(file.getOriginalFilename());
		Book b = bookRepository.save(book);
		if (null != b) {
			model.addAttribute("message", "Thêm mới thành công!");
			model.addAttribute("book", book);
		} else {
			model.addAttribute("message", "Thêm mới thất bại!");
			model.addAttribute("book", book);
		}
		model.addAttribute("message", "Thêm mới thành công!");

		return "redirect:/admin/books";
	}

	// Edit author
	@GetMapping(value = "/admin/updateBook")
	public String updateBook(@RequestParam("id") int id, Model model, @ModelAttribute("book") Book book) {
		Optional<Book> bookDto = bookRepository.findById(id);

		Book bookDsp = new Book();
		BeanUtils.copyProperties(bookDto.get(), bookDsp);
		model.addAttribute("book", bookDsp);
		return "admin/editBook";
	}
	
	// update category
	@PostMapping(value = "/admin/doUpdateBook/{id}")
	public String doUpdateBook(@PathVariable("id") Integer id, @ModelAttribute("book") Book book,
			Model model, RedirectAttributes rs) {
		book.setId(id);
		Book cs = bookRepository.save(book);
		if (cs != null) {
			model.addAttribute("message", "Update success");
			model.addAttribute("category", categoryRepository.findById(cs.getId()));
		} else {
			model.addAttribute("message", "Update failure");
			model.addAttribute("category", book);
		}

		return "redirect:/admin/books";
	}

	// delete author
	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") Integer id, Model model) {

		bookRepository.deleteById(id);
		return "redirect:/admin/books";
	}

}
