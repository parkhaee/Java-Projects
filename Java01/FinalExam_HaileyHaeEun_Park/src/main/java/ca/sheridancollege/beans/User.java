package ca.sheridancollege.beans;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private Long userId;		//should match what you used in database coloum names
    private String userName;
    private String encryptedPassword;
}

