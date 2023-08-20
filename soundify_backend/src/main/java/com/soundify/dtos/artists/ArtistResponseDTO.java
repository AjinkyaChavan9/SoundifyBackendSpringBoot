package com.soundify.dtos.artists;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ArtistResponseDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

}
