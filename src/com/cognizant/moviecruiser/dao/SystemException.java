package com.cognizant.moviecruiser.dao;

public class SystemException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String errorMessage;
	int errorCode;
	
	public SystemException(String errorMessage, int errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
	
	public String getMessage() {
		return ("Some error has occured to load this page!");
	}

}
