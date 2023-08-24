package com.soundify.custom_exceptions;

public class ResourceNotFoundException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -9140555206402054067L;
	
	//private final String status;

	public ResourceNotFoundException(String mesg) {
		super(mesg);
		//status="error";
	}
	
//	public String getStatus()
//	{
//		return status;
//	}
}
