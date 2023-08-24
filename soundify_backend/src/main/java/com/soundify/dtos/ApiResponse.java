package com.soundify.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter // to be used during ser.
@Setter // to be used during de-ser
public class ApiResponse {
	private String status;
	private String message;
	private LocalDateTime timeStamp;

	public ApiResponse(String status, String message) {
		super();
		this.status = status;
		this.message = message;
		this.timeStamp = LocalDateTime.now();
	}

}
