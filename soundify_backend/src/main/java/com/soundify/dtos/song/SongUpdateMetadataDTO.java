package com.soundify.dtos.song;

import java.sql.Time;
import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Data
public class SongUpdateMetadataDTO {
	
	private String songName;

	private Time duration;

	private LocalDate releaseDate;

	
}
