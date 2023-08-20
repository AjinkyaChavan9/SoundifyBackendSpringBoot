package com.soundify.dtos.playlists;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaylistResponseDTO {
	private Long id;
	private String playlistName;
}
