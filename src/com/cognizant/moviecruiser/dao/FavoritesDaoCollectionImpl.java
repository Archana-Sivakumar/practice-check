package com.cognizant.moviecruiser.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cognizant.moviecruiser.model.Favorites;
import com.cognizant.moviecruiser.model.Movies;

public class FavoritesDaoCollectionImpl implements FavoritesDao {
	private static Map<Long, Favorites> userFavorites;

	public FavoritesDaoCollectionImpl() {
		if (userFavorites == null) {
			userFavorites = new HashMap<Long, Favorites>();
		}
	}

	public static Map<Long, Favorites> getUserFavorites() {
		return userFavorites;
	}

	public static void setUserFavorites(Map<Long, Favorites> userFavorites) {
		FavoritesDaoCollectionImpl.userFavorites = userFavorites;
	}

	@Override
	public void addFavoriteList(long userId, long movieId) throws SystemException  {

		MoviesDao moviesDao = new MoviesDaoCollectionImpl();
		Movies movie = moviesDao.getMovieList(movieId);

		if (userFavorites.containsKey(userId)) {
			Favorites favorite = userFavorites.get(userId);
			favorite.getMovieList().add(movie);
			
			List<Movies> newMovieList = favorite.getMovieList();
			Set<Movies> newSet = new HashSet<Movies>();
		    newSet.addAll(newMovieList);
		    newMovieList = new ArrayList<Movies>();
		    newMovieList.addAll(newSet);
			favorite.getMovieList().clear();
			favorite.getMovieList().addAll(newMovieList);
			
		} else {
			Favorites favorite = new Favorites(new ArrayList<Movies>(), 0);
			favorite.getMovieList().add(movie);
			userFavorites.put(userId, favorite);
		}
	}

	@Override
	public Favorites getAllFavoriteList(long userId) throws FavoriteEmptyException {
		if (userFavorites.isEmpty()) {
			FavoriteEmptyException favoriteEmpty = new FavoriteEmptyException();
			throw favoriteEmpty;
		}
		else {
		int noOfFavorites = 0;
		Favorites favorite = userFavorites.get(userId);
		List<Movies> movie = favorite.getMovieList();

		if (movie.isEmpty()) {
			FavoriteEmptyException favoriteEmpty = new FavoriteEmptyException();
			throw favoriteEmpty;
		} else {
			for (int i = 0; i < movie.size(); i++) {
				noOfFavorites++;
				;
			}
			favorite.setNoOfFavorites(noOfFavorites);
			favorite.setMovieList(movie);
		}
		return favorite;
	}
	}
	@Override
	public void removeFavoriteList(long userId, long movieId) {

		Favorites favorite = userFavorites.get(userId);
		List<Movies> movie = favorite.getMovieList();

		for (int i = 0; i < movie.size(); i++) {
			if (movie.get(i).getId() == movieId) {
				movie.remove(i);
				break;
			}
		}
	}

}
