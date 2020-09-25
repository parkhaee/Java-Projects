package ca.sheridancollege.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Toy;
import ca.sheridancollege.database.DatabaseAccess;

@Controller
public class HomeController {
	
	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	@GetMapping("/")
	public String goHome()
	{
		return "index.html";
	}
	
	@GetMapping("/login")
	public String goLoginPage()
	{
		return "login.html";
	}
	
	@GetMapping("/access-denied")
	public String goAccessDenied()
	{
		return "/error/access-denied.html";
	}
	
	@GetMapping("/registration")
	public String goRegistration()
	{
		return "registration.html";
	}
	
	@PostMapping("/register")
	public String newRegister(@RequestParam String username, 
			@RequestParam String password, 
			@RequestParam(defaultValue = "false") String role1, 
			@RequestParam(defaultValue = "false") String role2)
	{
		da.createNewUser(username, password);
		long userId = da.findUserAccount(username).getUserId();
		
		if (role1.matches("boss")) {
			da.addRole(userId, 1);
		}
		if (role2.matches("worker")) {
			da.addRole(userId, 2);
		}

		return "redirect:/registration";
	}
	
	@GetMapping("/boss/add")
	public String goAddToy(Model model)
	{
		model.addAttribute("toy", new Toy());
		return "/boss/addToy.html";
	}
	
	@PostMapping("/boss/addToy")
	public String newRegister(Model model, @ModelAttribute Toy toy)
	{
		da.addToy(toy);
		return "redirect:/boss/add";
	}
	
	@GetMapping("/viewToys")
	public String viewToys(Model model)
	{
		model.addAttribute("toys", da.getToys());		
		return "viewToys.html";
	}

	@GetMapping("/error")
	public String error(Model model)
	{
		return "error.html";
	}
	
	@GetMapping("/boss/edit/{id}")
	public String editToy(@PathVariable int id, Model model)
	{
		Toy t = da.getToyById(id);
		model.addAttribute("toy", t);
		return "/boss/modifyToy.html";
	}
	
	@PostMapping("/modifyToy")
	public String modifyToy(Model model, @ModelAttribute Toy toy)
	{
		da.editToy(toy);
		return "redirect:/viewToys";
	}
	
	@GetMapping("/boss/delete/{id}")
	public String deleteToy(@PathVariable int id, Model model)
	{
		da.deleteToy(id);
		return "redirect:/viewToys";
	}
	
	@GetMapping("/search")
	public String searchToyHome(Model model)
	{
		return "/search/index.html";
	}
	
	@GetMapping("/searchName")
	public String searchName(Model model)
	{
		model.addAttribute("toy", new Toy());
		return "search/searchName.html";
	}
	
	@GetMapping("/searchByName")
	public String searchByTitle(Model model, @ModelAttribute Toy toy)
	{
		model.addAttribute("toy", da.searchByName(toy));
		return "search/result.html";
	}
	
	@GetMapping("/searchPrice")
	public String searchPrice(Model model)
	{
		model.addAttribute("toy", new Toy());
		return "search/searchPrice.html";
	}
	
	@GetMapping("/searchByPrice")
	public String searchByPrice(Model model, @RequestParam("min") double min, @RequestParam("max") double max)
	{
		model.addAttribute("min", min);
		model.addAttribute("max", max);
		model.addAttribute("toy", da.searchByPrice(min, max));
		return "search/result.html";
	}
	
	@GetMapping("/searchQuantity")
	public String searchQuantity(Model model)
	{
		model.addAttribute("toy", new Toy());
		return "search/searchQuantity.html";
	}
	
	@GetMapping("/searchByQuantity")
	public String searchByQuantity(Model model, @RequestParam("min") double min, @RequestParam("max") double max)
	{
		model.addAttribute("min", min);
		model.addAttribute("max", max);
		model.addAttribute("toy", da.searchByQuantity(min, max));
		return "search/result.html";
	}
	
}
