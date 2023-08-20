package com.soundify.dtos.user;

import lombok.Data;

@Data
public class PasswordChangeRequestDTO {
	
	private String oldPassword;
	private String newPassword;
	
}
