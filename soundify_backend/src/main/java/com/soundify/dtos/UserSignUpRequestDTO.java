package com.soundify.dtos;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class UserSignUpRequestDTO {

	
	private String firstName;

	private String lastName;
	
	private String email;

	private String password;
	
	private LocalDate dateOfBirth;
	
}
