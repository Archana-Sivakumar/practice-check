package com.cognizant.moviecruiser.dao;

public class FavoriteEmptyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FavoriteEmptyException() {

	}

	public String getMessage() {
		return ("Favorites is Empty!");
	}
}
