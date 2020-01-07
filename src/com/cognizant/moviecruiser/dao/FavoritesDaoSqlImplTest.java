package com.cognizant.moviecruiser.dao;

import java.util.List;

import com.cognizant.moviecruiser.model.Favorites;
import com.cognizant.moviecruiser.model.Movies;

public class FavoritesDaoSqlImplTest {

	public static void main(String[] args) {

		testAddFavorites();

		testRemoveFavorites();

	}

	public static void testAddFavorites() {

		System.out.println("Add the movie list into the favorites");
		System.out.println("----------------------------------");

		FavoritesDao favoriteDao = new FavoritesDaoSqlImpl();
		try {

			favoriteDao.addFavoriteList(1, 3);
			favoriteDao.addFavoriteList(1, 4);
			favoriteDao.addFavoriteList(1, 1);
			
			System.out.println("Display added favorite list");
			System.out.println("----------------------------------");

			Favorites favoriteObj = favoriteDao.getAllFavoriteList(1);
			List<Movies> movie = favoriteObj.getMovieList();

			for (Movies movies : movie) {
				System.out.println(movies);
				System.out.println();
			}

		} catch (FavoriteEmptyException favoriteEmpty) {

			System.out.println(favoriteEmpty.getMessage());

		} catch (SystemException systemException) {

			System.out.println(systemException.getMessage());
		}
	}

	public static void testGetAllFavoritesList() {
		
		FavoritesDao favoriteDao = new FavoritesDaoSqlImpl();
		
		try {
			
			Favorites favoriteObj = favoriteDao.getAllFavoriteList(1);
			List<Movies> movie = favoriteObj.getMovieList();
			
			for (int i = 0; i < movie.size(); i++) {
				System.out.println(movie.get(i));
			}
			
		} catch (FavoriteEmptyException favoriteEmpty) {
			
			System.out.println(favoriteEmpty.getMessage());
			
		} catch (SystemException systemException) {

			System.out.println(systemException.getMessage());
		}
	}

	public static void testRemoveFavorites() {

		System.out.println("Remove the movie list from the favorite");
		System.out.println("----------------------------------");

		FavoritesDao favoriteDao = new FavoritesDaoSqlImpl();
		
		try {
			
			favoriteDao.removeFavoriteList(1, 1);
			favoriteDao.removeFavoriteList(1, 4);
			favoriteDao.removeFavoriteList(1, 3);
			
			System.out.println("Display the movie list after removing from favorites");
			System.out.println("----------------------------------");
			
			Favorites favoriteObj = favoriteDao.getAllFavoriteList(1);
			List<Movies> movie = favoriteObj.getMovieList();
			
			for (int i = 0; i < movie.size(); i++) {
				System.out.println(movie.get(i));
			}
			
		} catch (SystemException systemException) {

			System.out.println(systemException.getMessage());
			
		} catch (FavoriteEmptyException favoriteEmpty) {
			
			System.out.println(favoriteEmpty.getMessage());
		}	
	}

}
