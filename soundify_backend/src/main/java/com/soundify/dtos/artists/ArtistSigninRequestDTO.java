package com.soundify.dtos.artists;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArtistSigninRequestDTO {
	// @NotBlank(message = "Email must be supplied")
//	@Length(min=4,max=20,message = "")
	// @Email(message = "Invalid Email")

	private String email;
	// @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[#@$*]).{5,20})", message =
	// "Blank or Invalid password format")
	private String password;
}
