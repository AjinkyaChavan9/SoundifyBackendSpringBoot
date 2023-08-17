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
@Table(name = "album") // to specify table name
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class Album extends BaseEntity {
   
	@Column(name = "album_name", length = 50)
	private String albumName;
	
	@Column(name = "album_release_date")
	private LocalDate albumReleaseDate;
	
	
}
