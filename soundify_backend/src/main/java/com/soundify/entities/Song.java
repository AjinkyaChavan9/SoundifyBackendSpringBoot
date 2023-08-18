package com.soundify.entities;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;//all specs Java EE supplied

import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.Getter;
import lombok.NoArgsConstructor;
/*
 *   <tr key={employee.id}>
                <td>{employee.firstName}</td>
                <td>{employee.lastName}</td>
                <td>{employee.email}</td>
                <td>{employee.location}</td>
                <td>{employee.department}</td>
                <td>{employee.joinDate}</td>
                <td>{employee.salary}</td>
                <td>
 */
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "songs") // to specify table name
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class Song extends BaseEntity {
	//course title(unique) , start date , end date , fees , min score
	@Column(name = "song_name", length = 50, unique = true) 
	private String songName;
	@Column(name = "duration")
	private Time duration;
	@Column(name = "song_release_date")
	private LocalDate releaseDate;
	
	@Column(name="song_path")
	private String songPath;
	
	@Column(name="song_image_path")
	private String songImagePath;
	
	@ManyToMany(mappedBy = "songs")
	private Set<Artist> artists = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name="song_likes", joinColumns = @JoinColumn(name="song_id"), inverseJoinColumns = @JoinColumn(name="user_id"))
	private Set<User> users = new HashSet<>();
	
	
	
	
}
	
	

	
	
	

