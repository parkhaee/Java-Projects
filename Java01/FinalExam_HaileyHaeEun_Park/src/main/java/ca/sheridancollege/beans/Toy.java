package ca.sheridancollege.beans;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Toy {

	private int id;
	private String name;
	private double price;
	private int quantity;
}
