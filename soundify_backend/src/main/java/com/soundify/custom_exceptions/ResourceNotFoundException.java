package com.soundify.custom_exceptions;

public class ResourceNotFoundException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -9140555206402054067L;

	public ResourceNotFoundException(String mesg) {
		super(mesg);
	}
}
