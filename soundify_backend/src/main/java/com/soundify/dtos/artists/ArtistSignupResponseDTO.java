package com.soundify.dtos.artists;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArtistSignupResponseDTO {
	private String name;

	private String firstName;

	private String lastName;

	private String email;

	private LocalDate dateOfBirth;

}
