package com.soundify.dtos.user;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSignupResponseDTO {
	
	private String firstName;

	private String lastName;
	
	private String email;

	private LocalDate dateOfBirth;
	
}
