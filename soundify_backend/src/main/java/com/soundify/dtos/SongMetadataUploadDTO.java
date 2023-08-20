package com.soundify.dtos;

import java.sql.Time;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class SongMetadataUploadDTO {
	
	private String songName;

	private Time duration;

	private LocalDate releaseDate;
	
	private String songPath;

}
