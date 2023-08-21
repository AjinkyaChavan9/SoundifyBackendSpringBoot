package com.soundify.dtos.artists;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString


public class ArtistSigninResponseDTO {
	private Long id;
	private String name;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	
}
