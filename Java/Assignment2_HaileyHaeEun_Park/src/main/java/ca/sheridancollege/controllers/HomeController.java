package ca.sheridancollege.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Book;
import ca.sheridancollege.beans.Course;
import ca.sheridancollege.beans.LibraryLocation;
import ca.sheridancollege.database.DatabaseAccess;

@Controller
public class HomeController {
	
	@Autowired
	private DatabaseAccess da;
	private String status = "enable";
	
	@GetMapping("/")
	public String goHome(Model model)
	{
		model.addAttribute("book", new Book());
		return "home.html";
	}
	
	@GetMapping("/dummy")
	public String generateDummy(Model model)
	{
		da.generateDummy();
		return "home.html";
	}
	
	@GetMapping("/add")
	public String goAdd(Model model)
	{
		model.addAttribute("book", new Book());
		return "add.html";
	}
	
	@GetMapping("/save")
	public String addBook(Model model, @ModelAttribute Book book)
	{
		da.addBook(book);
		return "successAdd.html";
	}

	@GetMapping("/error")
	public String error(Model model)
	{
		return "error.html";
	}
	
	@GetMapping("/view")
	public String viewList(Model model)
	{
		
		model.addAttribute("oakville", da.getBooksOakville());
		model.addAttribute("brampton", da.getBooksBrampton());
		model.addAttribute("mississauga", da.getBooksMississauga());
		model.addAttribute("status", status);
		
		return "view.html";
	}
	
	@GetMapping("/purchase")
	public String purchaseHome(Model model)
	{
		model.addAttribute("book", new Book());
		return "purchaseHome.html";
	}
	
	@GetMapping("/edit/{id}")
	public String editBook(@PathVariable int id, Model model)
	{
		Book b = da.getBookById(id);
		model.addAttribute("book", b);
		return "modify.html";
	}
	
	@GetMapping("/modify")
	public String modifyBook(Model model, @ModelAttribute Book book)
	{
		da.editBook(book);
		return "redirect:/view";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id, Model model)
	{
		da.deleteBook(id);
		return "redirect:/view";
	}
	
	@GetMapping("/purchase/{id}")
	public String purchaseBook(@PathVariable int id, @ModelAttribute Book book, Model model)
	{
		da.purchaseBook(id);
//		
//		if (da.getBookById(id).getQuantity() == 0)
//			status = "disable";
//		else 
//			status = "enable";
			
		return "redirect:/view";
	}
	
	@GetMapping("/purchaseResult/{id}")
	public String purchaseBookResult(@PathVariable int id, @ModelAttribute Book book, Model model)
	{
		da.purchaseBook(id);
		model.addAttribute("book", da.getBookById2(id));
		return "search/result.html";
	}
	
	@GetMapping("/search")
	public String searchHome(Model model)
	{
		model.addAttribute("book", new Book());
		return "search/searchHome.html";
	}
	
	@GetMapping("/searchId")
	public String searchId(Model model)
	{
		model.addAttribute("book", new Book());
		return "search/searchId.html";
	}
	
	@GetMapping("/searchById")
	public String searchById(Model model, @ModelAttribute Book book)
	{
		model.addAttribute("book", da.searchById(book));
		return "search/result.html";
	}
	
	@GetMapping("/searchTitle")
	public String searchByTitle(Model model)
	{
		model.addAttribute("book", new Book());
		return "search/searchTitle.html";
	}
	
	@GetMapping("/searchByTitle")
	public String searchByTitle(Model model, @ModelAttribute Book book)
	{
		model.addAttribute("book", da.searchByTitle(book));
		return "search/result.html";
	}
	
	@GetMapping("/searchAuthor")
	public String searchByAuthor(Model model)
	{
		model.addAttribute("book", new Book());
		return "search/searchAuthor.html";
	}
	
	@GetMapping("/searchByAuthor")
	public String searchByAuthor(Model model, @ModelAttribute Book book)
	{
		model.addAttribute("book", da.searchByAuthor(book));
		return "search/result.html";
	}
	
	@GetMapping("/searchCourse")
	public String searchCourse(Model model)
	{
		model.addAttribute("book", new Book());
		return "search/searchCourse.html";
	}
	
	@GetMapping("/searchByCourse")
	public String searchByCourse(Model model, @RequestParam String courses)
	{
		model.addAttribute("book", da.searchByCourse(courses));
		return "search/result.html";
	}
	
	@GetMapping("/searchQuantity")
	public String searchByQuantity(Model model)
	{
		model.addAttribute("book", new Book());
		return "search/searchQuantity.html";
	}
	
	@GetMapping("/searchByQuantity")
	public String searchByQuantity(Model model, @RequestParam int quantity)
	{
		model.addAttribute("book", da.searchByQuantity(quantity));
		return "search/result.html";
	}
	
}
