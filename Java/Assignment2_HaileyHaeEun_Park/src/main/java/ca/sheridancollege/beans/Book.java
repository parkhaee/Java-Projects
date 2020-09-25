package ca.sheridancollege.beans;

import java.util.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book implements java.io.Serializable {
	
	private static final long serialVersionUID = 994527278935999124L;
	
	private int id;
	private String title;
	private String author;
	private double price;
	private int quantity;
//	private List<Course> courses = new ArrayList<>();
	
	private List<String> courses = new ArrayList<>(Arrays.asList("Enterprise Java", "Computer Math", 
			"Web Programming", "Canadian History", "Music", "Biology", "Physics", "Microeconomics",
			"English", "Political Science", "Psychology", "Spanish", "Canadian Law", "Business Fundamental",
			"Visual Art", "Foods and Nutrition", "World History", "Chemistry", "Earth Science"));

	
	private LibraryLocation location;
	private List<LibraryLocation> locations = Arrays.asList(LibraryLocation.values());
	
	public Book(int id, String title, String author, double price, int quantity) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.price = price;
		this.quantity = quantity;
	}

}
