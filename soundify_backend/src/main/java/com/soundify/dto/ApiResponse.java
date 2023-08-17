package com.soundify.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter //to be used during ser.
@Setter //to be used during de-ser
public class ApiResponse {
	private String message;
	private LocalDateTime timeStamp;
	public ApiResponse(String message) {
		super();
		this.message = message;
		this.timeStamp=LocalDateTime.now();
	}
	
}
