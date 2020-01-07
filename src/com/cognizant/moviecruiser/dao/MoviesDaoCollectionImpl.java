package com.cognizant.moviecruiser.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cognizant.moviecruiser.model.Movies;
import com.cognizant.moviecruiser.util.DateUtil;

public class MoviesDaoCollectionImpl implements MoviesDao {
	private static List<Movies> movieList;

	public MoviesDaoCollectionImpl() {

		if (movieList == null) {
			movieList = new ArrayList<Movies>();
			movieList.add(new Movies(1, "Avatar", 2787965087L, true, DateUtil.convertToDate("15/03/2017"),
					"Science Fiction", true));
			movieList.add(new Movies(2, "The Avengers", 1518812988L, true, DateUtil.convertToDate("23/12/2017"),
					"Superhero", false));
			movieList.add(new Movies(3, "Titanic", 2187463944L, true, DateUtil.convertToDate("21/08/2017"), "Romance",
					false));
			movieList.add(new Movies(4, "Jurassic World", 1671713208L, false, DateUtil.convertToDate("02/07/2017"),
					"Science Fiction", true));
			movieList.add(new Movies(5, "Avengers: End Game", 2750760348L, true, DateUtil.convertToDate("02/11/2022"),
					"Superhero", true));
		}
	}

	public static List<Movies> getMovieList() {
		return movieList;
	}

	public static void setMovieList(List<Movies> movieList) {
		MoviesDaoCollectionImpl.movieList = movieList;
	}

	@Override
	public List<Movies> getMovieListAdmin() {
		return movieList;
	}

	@Override
	public List<Movies> getMovieListCustomer() {

		List<Movies> filteredMovieList = new ArrayList<Movies>();
		for (Movies movie : movieList) {
			int dateCompare = movie.getDateOfLaunch().compareTo(new Date());
			if (movie.isActive() == true && (dateCompare == 0 || dateCompare < 0)) {
				filteredMovieList.add(movie);
			}
		}
		return filteredMovieList;
	}

	@Override
	public void editMovieList(Movies movie) {
		int i;
		for (i = 0; i < movieList.size(); i++) {
			if (movie.getId() == movieList.get(i).getId()) {
				movieList.set(i, movie);
				break;
			}
		}

	}

	@Override
	public Movies getMovieList(long movieId) {

		int i;
		Movies movie = null;
		for (i = 0; i < movieList.size(); i++) {
			if (movieId == movieList.get(i).getId()) {
				movie = movieList.get(i);
				break;
			}
		}
		return movie;
	}

}
