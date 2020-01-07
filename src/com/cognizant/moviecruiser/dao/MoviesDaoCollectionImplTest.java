package com.cognizant.moviecruiser.dao;

import java.util.List;

import com.cognizant.moviecruiser.model.Movies;
import com.cognizant.moviecruiser.util.DateUtil;

public class MoviesDaoCollectionImplTest {

	public static void main(String[] args) {

		testGetMovieListAdmin();

		testGetMovieListCustomer();

		testModifyMovieList();
	}

	private static void testGetMovieListAdmin() {

		System.out.println("*****WELCOME*****");
		System.out.println("Admin movie list");
		System.out.println("---------------------");

		MoviesDao moviesDao = new MoviesDaoCollectionImpl();
		
		try {
			
			for (Movies movie : moviesDao.getMovieListAdmin()) {
				System.out.println(movie);
				System.out.println();
			}
			
		} catch (SystemException systemException) {
			
			System.out.println(systemException.getMessage());
		}
	}

	private static void testGetMovieListCustomer() {

		System.out.println("Customer movie list");
		System.out.println("------------------------");
		
		MoviesDao moviesDao = new MoviesDaoCollectionImpl();
		
		try {
			
			List<Movies>  moviesList = moviesDao.getMovieListCustomer();
			for (int i = 0; i < moviesList.size(); i++) {
				System.out.println(moviesList.get(i));
				System.out.println();
			}

		} catch (SystemException systemException) {
			
			System.out.println(systemException.getMessage());
		}
	}

	private static void testModifyMovieList() {

		Movies movie = new Movies(1, "Fast & Furious", 2787965087L, true, DateUtil.convertToDate("15/03/2017"),
				"Science Fiction", true);
		MoviesDao moviesDao = new MoviesDaoCollectionImpl();
		
		try {
			moviesDao.editMovieList(movie);
			
			System.out.println("Display the modified movie");
			System.out.println("------------------------------");
			
			movie = moviesDao.getMovieList(1);
			System.out.println(movie);
			
		} catch (SystemException systemException) {
			
			System.out.println(systemException.getMessage());
		}

		

		
		
	}
}
