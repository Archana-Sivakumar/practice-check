package com.cognizant.moviecruiser.dao;

import java.util.List;

import com.cognizant.moviecruiser.model.Movies;

public interface MoviesDao {
	public List<Movies> getMovieListAdmin() throws SystemException;

	public List<Movies> getMovieListCustomer() throws SystemException;

	public void editMovieList(Movies movies) throws SystemException ;

	public Movies getMovieList(long movieId) throws SystemException;

}
