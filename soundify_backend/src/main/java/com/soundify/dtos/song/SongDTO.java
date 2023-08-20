package com.soundify.dtos.song;

import java.sql.Time;
import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Data
public class SongDTO {
     
	private long Id;
	private String songName;

	private Time duration;

	private LocalDate releaseDate;

	private String songPath;

	private String songImagePath;
	// Add other fields as needed
}
