package ca.sheridancollege.database;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Book;
import ca.sheridancollege.beans.Course;
import ca.sheridancollege.beans.LibraryLocation;

@Repository
public class DatabaseAccess {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	public void generateDummy()
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO BOOKS (title, author, price, quantity, courses, location)"
				+ "VALUES ('Fundamentals of Physics', 'David Halliday', 200.92, 8, 'Physics,Chemistry', 'OAKVILLE'),"
				+ "('Think Java', 'Chris Mayfield', 129.99, 3, 'Enterprise Java', 'OAKVILLE'),"
				+ "('Introduction to Programming Using Java', 'David Hobart', 149.89, 5, 'Enterprise Java', 'OAKVILLE'),"
				+ "('Introduction to Linear Algebra', 'Gilbert Strang', 100.89, 3, 'Physics,Computer Math,Microeconomics', 'OAKVILLE'),"
				+ "('Organic Chemistry', 'Leroy Wade', 220.00, 3, 'Chemistry', 'OAKVILLE'),"
				+ "('Principles of Microeconomics', 'Gregory Mankiw', 112.02, 5, 'Microeconomics', 'OAKVILLE'),"
				+ "('Concepts of Biology', 'Samantha Fowler', 25.00, 2, 'Biology', 'OAKVILLE'),"
				+ "('Molecular Biology of The Cell', 'Bruce Alberts', 168.96, 3, 'Biology,Chemistry', 'OAKVILLE'),"
				+ "('HTML and CSS: Design and Build Websites', 'Jon Duckett', 29.84, 4, 'Web Programming,Enterprise Java', 'OAKVILLE'),"
				+ "('Head First HTML and CSS', 'Elisabeth Robson', 49.99, 6, 'Web Programming,Enterprise Java', 'OAKVILLE'),"
				+ "('Gateways to Art', 'Debra DeWitte', 123.57, 6, 'Visual Art', 'BRAMPTON'),"
				+ "('Think Java', 'Chris Mayfield', 129.99, 5, 'Enterprise Java', 'BRAMPTON'),"
				+ "('Introduction to Programming Using Java', 'David Hobart', 149.89, 2, 'Enterprise Java', 'BRAMPTON'),"
				+ "('Introduction to Linear Algebra', 'Gilbert Strang', 100.89, 1, 'Physics,Computer Math,Microeconomics', 'BRAMPTON'),"
				+ "('A Concise History of Canada', 'Margaret Conrad', 109.00, 4, 'Canadian History,Canadian Law,Political Science,World History', 'BRAMPTON'),"
				+ "('Principles of Microeconomics', 'Gregory Mankiw', 112.02, 2, 'Microeconomics', 'BRAMPTON'),"
				+ "('The Canadian Frontier', 'W.J.Eccles', 809.67, 2, 'Canadian History,World History', 'BRAMPTON'),"
				+ "('Complete English Grammer Rules', 'Farlex International', 29.99, 5, 'English,Business Fundamental', 'BRAMPTON'),"
				+ "('Understanding Business', 'William Nickels', 25.33, 7, 'Business Fundamental', 'BRAMPTON'),"
				+ "('Better Business', 'Michael Solomon', 31.33, 6, 'Business Fundamental', 'BRAMPTON'),"
				+ "('Business Law:Text and Cases', 'Kenneth Clarkson', 237.45, 3, 'Business Fundamental,Canadian Law', 'MISSISSAUGA'),"
				+ "('Easy Spanish Step-By-Step', 'Barbara Bregstein', 11.40, 5, 'Spanish', 'MISSISSAUGA'),"
				+ "('Complete Spanish Grammer', 'Gilda Nissenberg', 12.15, 5, 'Spanish', 'MISSISSAUGA'),"
				+ "('Models for Writers:Short Essays for Composition', 'Alfred Rosa', 79.48, 3, 'English,Business Fundamental', 'MISSISSAUGA'),"
				+ "('Connections:Food, Nutrition, Health and Wellness', 'Mary Eaton', 135.00, 6, 'Foods and Nutrition,Biology', 'MISSISSAUGA'),"
				+ "('Understanding Food:Principles and Preparation', 'Amy Brown', 110.12, 5, 'Foods and Nutrition', 'MISSISSAUGA'),"
				+ "('The Music Products Industry', 'Carl Anderson', 24.95, 5, 'Music,Business Fundamental', 'MISSISSAUGA'),"
				+ "('Music:The Art of Listening', 'Jean Ferris', 131.68, 3, 'Music', 'MISSISSAUGA'),"
				+ "('Earth Science', 'Edward Tarbuck', 154.66, 4, 'Earth Science', 'MISSISSAUGA'),"
				+ "('Essentials of Geology', 'Frederick Lutgens', 30.07, 6, 'Earth Science', 'MISSISSAUGA')";
		jdbc.update(query, parameters);
	}
	
	public void addBook(Book book)
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		int size = book.getCourses().size();
		String courseList = null;
		
		for (int i = 0; i < size; i++)
		{
			if (courseList == null)
			courseList = book.getCourses().get(i);
			
			else
				courseList = courseList + "," + book.getCourses().get(i);
		}
		
		String query = "INSERT INTO BOOKS (title, author, price, quantity, courses, location)"
				+ "VALUES (:title, :author, :price, :quantity, :courses, :locations)";
		
		parameters.addValue("title", book.getTitle());
		parameters.addValue("author", book.getAuthor());
		parameters.addValue("price", book.getPrice());
		parameters.addValue("quantity", book.getQuantity());
		parameters.addValue("courses", courseList);
		parameters.addValue("locations", book.getLocations().get(0).name());
		
		jdbc.update(query, parameters);
		}	
	
	public ArrayList<Book> getBooksOakville()
	{
		ArrayList<Book> books = new ArrayList<Book>();
		
		String query = "SELECT * FROM BOOKS WHERE location ='OAKVILLE'";
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
		
		for (Map<String, Object> row: rows)
		{
			Book b = new Book();
//			Course c = new Course();
			
			String course = (String) row.get("courses");
			String[] csvList = course.split(",");
			List<String> listOfStrings = Arrays.asList(csvList);
			
//			c.setCourseName(listOfStrings.toString());
//			b.getCourses().add(c);
			b.setId((Integer)row.get("id"));
			b.setTitle((String)row.get("title"));
			b.setAuthor((String)row.get("author"));
			b.setPrice((Double)row.get("price"));
			b.setQuantity((Integer)row.get("quantity"));
			b.setCourses(listOfStrings);
			b.getCourses().toString();
			books.add(b);
			
//			if(b.getQuantity()==0)
//				
			
		}
		return books;
	}
	
	public ArrayList<Book> getBooksBrampton()
	{
		ArrayList<Book> books = new ArrayList<Book>();
		
		String query = "SELECT * FROM BOOKS WHERE location = 'BRAMPTON'";

		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
		
		for (Map<String, Object> row: rows)
		{
			Book b = new Book();
//			Course c = new Course();
			
			String course = (String) row.get("courses");
			String[] csvList = course.split(",");
			List<String> listOfStrings = Arrays.asList(csvList);
			
//			c.setCourseName(course);
//			b.getCourses().add(c);
			b.setId((Integer)row.get("id"));
			b.setTitle((String)row.get("title"));
			b.setAuthor((String)row.get("author"));
			b.setPrice((Double)row.get("price"));
			b.setQuantity((Integer)row.get("quantity"));
			b.setCourses(listOfStrings);
			books.add(b);
		}
		return books;
	}
	
	public ArrayList<Book> getBooksMississauga()
	{
		ArrayList<Book> books = new ArrayList<Book>();
		
		String query = "SELECT * FROM BOOKS WHERE location = 'MISSISSAUGA'";

		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
		
		for (Map<String, Object> row: rows)
		{
			Book b = new Book();
//			Course c = new Course();
			
			String course = (String) row.get("courses");
			String[] csvList = course.split(",");
			List<String> listOfStrings = Arrays.asList(csvList);
			
//			c.setCourseName(course);
//			b.getCourses().add(c);
			b.setId((Integer)row.get("id"));
			b.setTitle((String)row.get("title"));
			b.setAuthor((String)row.get("author"));
			b.setPrice((Double)row.get("price"));
			b.setQuantity((Integer)row.get("quantity"));
			b.setCourses(listOfStrings);
			books.add(b);
		}
		return books;
	}
	
	//for editing purpose
	public Book getBookById(int id)
	{
		ArrayList<Book> books = new ArrayList<Book>();
		
		
		String query = "SELECT * FROM BOOKS WHERE id=:id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row: rows)
		{
//			String location = (String) row.get("locations");
//			List<String> listOfStrings = Arrays.asList(location);
			
			Book b = new Book();
			
//			String course = (String) row.get("courses");
//			String[] csvList = course.split(",");
//			List<String> listOfStrings = Arrays.asList(csvList);
			
			String location = (String)row.get("location");
			if (location.matches("OAKVILLE"))
				b.setLocation(LibraryLocation.OAKVILLE);
			else if (location.matches("BRAMPTON"))
				b.setLocation(LibraryLocation.BRAMPTON);
			else if (location.matches("MISSISSAUGA"))
				b.setLocation(LibraryLocation.MISSISSAUGA);
			
			b.setId((Integer)row.get("id"));
			b.setTitle((String)row.get("title"));
			b.setAuthor((String)row.get("author"));
			b.setPrice((Double)row.get("price"));
			b.setQuantity((Integer)row.get("quantity"));
//			b.setCourses(listOfStrings);

			books.add(b);
		}
		if (books.size()>0)
			return books.get(0);
		
		return null;
	}
	
	//for displaying purpose after purchasing
	public Book getBookById2(int id)
	{
		ArrayList<Book> books = new ArrayList<Book>();
		
		
		String query = "SELECT * FROM BOOKS WHERE id=:id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row: rows)
		{
			
			Book b = new Book();
			
			String course = (String) row.get("courses");
			String[] csvList = course.split(",");
			List<String> listOfStrings = Arrays.asList(csvList);
			
			String location = (String)row.get("location");
			if (location.matches("OAKVILLE"))
				b.setLocation(LibraryLocation.OAKVILLE);
			else if (location.matches("BRAMPTON"))
				b.setLocation(LibraryLocation.BRAMPTON);
			else if (location.matches("MISSISSAUGA"))
				b.setLocation(LibraryLocation.MISSISSAUGA);
			
			b.setId((Integer)row.get("id"));
			b.setTitle((String)row.get("title"));
			b.setAuthor((String)row.get("author"));
			b.setPrice((Double)row.get("price"));
			b.setQuantity((Integer)row.get("quantity"));
			b.setCourses(listOfStrings);

			books.add(b);
		}
		if (books.size()>0)
			return books.get(0);
		
		return null;
	}
	
	//searching purpose
	public ArrayList<Book> searchById(Book book)
	{
		ArrayList<Book> books = new ArrayList<Book>();
		
		String query = "SELECT * FROM BOOKS WHERE id=:id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", book.getId());
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row: rows)
		{
			
			
			Book b = new Book();
			
			String course = (String) row.get("courses");
			String[] csvList = course.split(",");
			List<String> listOfStrings = Arrays.asList(csvList);
			
			String location = (String)row.get("location");
			if (location.matches("OAKVILLE"))
				b.setLocation(LibraryLocation.OAKVILLE);
			else if (location.matches("BRAMPTON"))
				b.setLocation(LibraryLocation.BRAMPTON);
			else if (location.matches("MISSISSAUGA"))
				b.setLocation(LibraryLocation.MISSISSAUGA);
			
			b.setId((Integer)row.get("id"));
			b.setTitle((String)row.get("title"));
			b.setAuthor((String)row.get("author"));
			b.setPrice((Double)row.get("price"));
			b.setQuantity((Integer)row.get("quantity"));
			b.setCourses(listOfStrings);
			books.add(b);
		}
		
		return books;
	}
	
	public ArrayList<Book> searchByTitle(Book book)
	{
		ArrayList<Book> books = new ArrayList<Book>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM BOOKS WHERE LOWER(title) LIKE LOWER('%'||:title||'%')";
		parameters.addValue("title", book.getTitle());
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row: rows)
		{
			Book b = new Book();
			
			String course = (String) row.get("courses");
			String[] csvList = course.split(",");
			List<String> listOfStrings = Arrays.asList(csvList);
			
			String location = (String)row.get("location");
			if (location.matches("OAKVILLE"))
				b.setLocation(LibraryLocation.OAKVILLE);
			else if (location.matches("BRAMPTON"))
				b.setLocation(LibraryLocation.BRAMPTON);
			else if (location.matches("MISSISSAUGA"))
				b.setLocation(LibraryLocation.MISSISSAUGA);
			
			b.setId((Integer)row.get("id"));
			b.setTitle((String)row.get("title"));
			b.setAuthor((String)row.get("author"));
			b.setPrice((Double)row.get("price"));
			b.setQuantity((Integer)row.get("quantity"));
			b.setCourses(listOfStrings);
			books.add(b);
		}
		return books;
	}
	
	public ArrayList<Book> searchByAuthor(Book book)
	{
		ArrayList<Book> books = new ArrayList<Book>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM BOOKS WHERE LOWER(author) LIKE LOWER('%'||:author||'%')";
		parameters.addValue("author", book.getAuthor());
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row: rows)
		{
			Book b = new Book();
			
			String course = (String) row.get("courses");
			String[] csvList = course.split(",");
			List<String> listOfStrings = Arrays.asList(csvList);
			
			String location = (String)row.get("location");
			if (location.matches("OAKVILLE"))
				b.setLocation(LibraryLocation.OAKVILLE);
			else if (location.matches("BRAMPTON"))
				b.setLocation(LibraryLocation.BRAMPTON);
			else if (location.matches("MISSISSAUGA"))
				b.setLocation(LibraryLocation.MISSISSAUGA);
			
			b.setId((Integer)row.get("id"));
			b.setTitle((String)row.get("title"));
			b.setAuthor((String)row.get("author"));
			b.setPrice((Double)row.get("price"));
			b.setQuantity((Integer)row.get("quantity"));
			b.setCourses(listOfStrings);
			books.add(b);
		}
		return books;
	}
	
	public ArrayList<Book> searchByCourse(String courses)
	{
		ArrayList<Book> books = new ArrayList<Book>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("courses", courses);
		String query = "SELECT * FROM BOOKS WHERE courses LIKE '%'||:courses||'%'";
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row: rows)
		{
			Book b = new Book();
			String course1 = (String) row.get("courses");
			String[] csvList = course1.split(",");
			List<String> listOfStrings = Arrays.asList(csvList);
			
			String location = (String)row.get("location");
			if (location.matches("OAKVILLE"))
				b.setLocation(LibraryLocation.OAKVILLE);
			else if (location.matches("BRAMPTON"))
				b.setLocation(LibraryLocation.BRAMPTON);
			else if (location.matches("MISSISSAUGA"))
				b.setLocation(LibraryLocation.MISSISSAUGA);
			
			b.setId((Integer)row.get("id"));
			b.setTitle((String)row.get("title"));
			b.setAuthor((String)row.get("author"));
			b.setPrice((Double)row.get("price"));
			b.setQuantity((Integer)row.get("quantity"));
			b.setCourses(listOfStrings);
			books.add(b);
		}
		return books;
	}
	
	public ArrayList<Book> searchByQuantity(int quantity)
	{
		ArrayList<Book> books = new ArrayList<Book>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("quantity", quantity);
		String query = "SELECT * FROM BOOKS WHERE quantity <=:quantity";
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row: rows)
		{
			Book b = new Book();
			String course1 = (String) row.get("courses");
			String[] csvList = course1.split(",");
			List<String> listOfStrings = Arrays.asList(csvList);
			
			String location = (String)row.get("location");
			if (location.matches("OAKVILLE"))
				b.setLocation(LibraryLocation.OAKVILLE);
			else if (location.matches("BRAMPTON"))
				b.setLocation(LibraryLocation.BRAMPTON);
			else if (location.matches("MISSISSAUGA"))
				b.setLocation(LibraryLocation.MISSISSAUGA);
			
			b.setId((Integer)row.get("id"));
			b.setTitle((String)row.get("title"));
			b.setAuthor((String)row.get("author"));
			b.setPrice((Double)row.get("price"));
			b.setQuantity((Integer)row.get("quantity"));
			b.setCourses(listOfStrings);
			books.add(b);
		}
		return books;
	}
	
	public void editBook(Book book)
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		int size = book.getCourses().size();
		String courseList = null;
		
		for (int i = 0; i < size; i++)
		{
			if (courseList == null)
			courseList = book.getCourses().get(i);
			
			else
				courseList = courseList + "," + book.getCourses().get(i);
		}
		
		String query = "UPDATE BOOKS SET title=:title, author=:author, price=:price,"
				+ "quantity=:quantity, courses=:courses, location=:location WHERE id=:id";
		
		parameters.addValue("id", book.getId());
		parameters.addValue("title", book.getTitle());
		parameters.addValue("author", book.getAuthor());
		parameters.addValue("price", book.getPrice());
		parameters.addValue("quantity", book.getQuantity());
		parameters.addValue("courses", courseList);
		parameters.addValue("location", book.getLocations().get(0).name());
		
		jdbc.update(query, parameters);
	}
	
	public void deleteBook(int id)
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM BOOKS WHERE id=:id";
		parameters.addValue("id", id);
		jdbc.update(query, parameters);
	}
	
	public void purchaseBook(int id)
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "UPDATE BOOKS SET quantity=quantity-1 WHERE id=:id AND quantity>0";
		parameters.addValue("id", id);
		
		jdbc.update(query, parameters);
	}
}