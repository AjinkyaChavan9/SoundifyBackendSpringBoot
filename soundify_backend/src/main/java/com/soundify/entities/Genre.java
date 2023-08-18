package com.soundify.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "genre") // to specify table name
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class Genre extends BaseEntity{

	@Column(name = "genre_name", length = 35)
	private String genreName;
	
	@ManyToMany(mappedBy = "genres")
	private Set<Song> songs = new HashSet<>();
}
