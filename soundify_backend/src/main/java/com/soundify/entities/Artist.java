package com.soundify.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "artists") // to specify table name
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class Artist {
	
	@Column(name = "artist_first_name", length=50)
	private String firstName;
	@Column(name = "artist_last_name", length=50)
	private String lastName;
	@Column(length=60, unique = true)
	private String email;
	@Column(length=50)
	private String password;
	@Column(name="date_of_birth")
	private LocalDate dateOfBirth;
		
}
