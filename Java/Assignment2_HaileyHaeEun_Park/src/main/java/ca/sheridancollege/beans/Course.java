package ca.sheridancollege.beans;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course implements java.io.Serializable{
	
	private static final long serialVersionUID = 6938570579600694154L;
	private int id;
	private String courseCode;
	private String courseName;
}
