package com.soundify.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "role") // to specify table name
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class Role extends BaseEntity {

	@Column(name = "role_name", length = 20, unique = true)
	private String roleName;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<User> userList = new ArrayList<>();

}
