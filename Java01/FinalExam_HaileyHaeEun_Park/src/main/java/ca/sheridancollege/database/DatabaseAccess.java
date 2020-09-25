package ca.sheridancollege.database;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;


import ca.sheridancollege.beans.Toy;
import ca.sheridancollege.beans.User;


@Repository
public class DatabaseAccess {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	public void addToy(Toy toy)
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "INSERT INTO toys ("
				+ "name, price, quantity)"
				+ "VALUES (:name, :price, :quantity)";
		
		parameters.addValue("name", toy.getName());
		parameters.addValue("price", toy.getPrice());
		parameters.addValue("quantity", toy.getQuantity());
		jdbc.update(query, parameters);
		}	
	
	public ArrayList<Toy> getToys()
	{
		String query = "SELECT * FROM toys";
        ArrayList<Toy> toys =
    		(ArrayList<Toy>)jdbc.query(query, new BeanPropertyRowMapper<Toy>(Toy.class));
    	return toys;
	}
	
	public ArrayList<Toy> searchByName(Toy toy)
	{
		ArrayList<Toy> toys = new ArrayList<Toy>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM toys WHERE LOWER(name) LIKE LOWER('%'||:name||'%')";
		parameters.addValue("name", toy.getName());
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row: rows)
		{
			Toy t = new Toy();
			
			t.setId((Integer)row.get("id"));
			t.setName((String)row.get("name"));
			t.setPrice((Double)row.get("price"));
			t.setQuantity((Integer)row.get("quantity"));

			toys.add(t);
		}
		return toys;
	}
	
	public ArrayList<Toy> searchByPrice(double min, double max)
	{
		ArrayList<Toy> toys = new ArrayList<Toy>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM toys WHERE price >=:min AND price <=:max ORDER BY id";
		parameters.addValue("min", min);
		parameters.addValue("max", max);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row: rows)
		{
			Toy t = new Toy();
			
			t.setId((Integer)row.get("id"));
			t.setName((String)row.get("name"));
			t.setPrice((Double)row.get("price"));
			t.setQuantity((Integer)row.get("quantity"));
			toys.add(t);
		}
		return toys;
	}
	
	public ArrayList<Toy> searchByQuantity(double min, double max)
	{
		ArrayList<Toy> toys = new ArrayList<Toy>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM toys WHERE quantity >=:min AND quantity <=:max ORDER BY id";
		parameters.addValue("min", min);
		parameters.addValue("max", max);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row: rows)
		{
			Toy t = new Toy();
			
			t.setId((Integer)row.get("id"));
			t.setName((String)row.get("name"));
			t.setPrice((Double)row.get("price"));
			t.setQuantity((Integer)row.get("quantity"));
			toys.add(t);
		}
		return toys;
	}
	
	//for editing purpose
	public Toy getToyById(int id)
	{
		ArrayList<Toy> toys = new ArrayList<Toy>();
		
		String query = "SELECT * FROM toys WHERE id=:id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row: rows)
		{			
			Toy s = new Toy();
						
			s.setId((Integer)row.get("id"));
			s.setName((String)row.get("name"));
			s.setPrice((Double)row.get("price"));
			s.setQuantity((Integer)row.get("quantity"));
			toys.add(s);
		}
		if (toys.size()>0)
			return toys.get(0);
		
		return null;
	}
	
	public void editToy(Toy toy)
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "UPDATE toys SET name=:name, price=:price, quantity=:quantity WHERE id=:id";
		
		parameters.addValue("id", toy.getId());
		parameters.addValue("name", toy.getName());
		parameters.addValue("price", toy.getPrice());
		parameters.addValue("quantity", toy.getQuantity());
		
		jdbc.update(query, parameters);
	}
	
	public void deleteToy(int id)
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM toys WHERE id=:id";
		parameters.addValue("id", id);
		jdbc.update(query, parameters);
	}

	public User findUserAccount(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user where userName=:userName";
		parameters.addValue("userName", userName);
		ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size()>0)
			return users.get(0);
		else
			return null;
	}
	
	public List<String> getRolesById(long userId) {
		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "select user_role.userId, sec_role.roleName "
				+ "FROM user_role, sec_role "
				+ "WHERE user_role.roleId=sec_role.roleId "
				+ "and userId=:userId";
		parameters.addValue("userId", userId);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
			roles.add((String)row.get("roleName"));
		}
		return roles;
	}
	
	public void createNewUser(String username, String password) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into SEC_User (userName, encryptedPassword, ENABLED)" + 
				"values (:name, :password, 1)";
		parameters.addValue("name", username);
		parameters.addValue("password", passwordEncoder.encode(password)); //need to encode the password
		jdbc.update(query, parameters);
	}
	
	public void addRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into user_role (userId, roleId)" + 
				"values (:userId, :roleId)";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);
	}
}