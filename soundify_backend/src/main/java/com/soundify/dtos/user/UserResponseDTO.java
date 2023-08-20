package com.soundify.dtos.user;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserResponseDTO {
            private Long id;
		    private String firstName;
		    private String lastName;
		    private String email;
		    private LocalDate dateOfBirth;
	}

