package com.soundify.dtos.artists;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArtistSignupRequestDTO {
	// To use a property only for ser.(i.e skip it during de-ser)
//	@JsonProperty(access = Access.READ_ONLY)
//	private Long id;

	private String name;
	// firstName can't be blank ,length 4--20(@Length),
	// @Length(min=4,max=20,message = "Invalid email length!!!")
	private String firstName;

	private String lastName;

	private String email;

	// To use a property only for de-ser.(i.e skip it during ser)
//	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	// DOB in future
	// @Future(message = "date of birth must be in future!!!")
	private LocalDate dateOfBirth;

}
