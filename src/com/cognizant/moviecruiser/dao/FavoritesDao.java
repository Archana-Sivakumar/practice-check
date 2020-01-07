package com.cognizant.moviecruiser.dao;

import com.cognizant.moviecruiser.model.Favorites;

public interface FavoritesDao {
	public void addFavoriteList(long userId, long movieId) throws SystemException;

	public Favorites getAllFavoriteList(long userId) throws FavoriteEmptyException, SystemException;

	public void removeFavoriteList(long userId, long movieId) throws SystemException;
}
