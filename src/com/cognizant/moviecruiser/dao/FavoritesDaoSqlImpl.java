package com.cognizant.moviecruiser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.cognizant.moviecruiser.model.Favorites;
import com.cognizant.moviecruiser.model.Movies;

public class FavoritesDaoSqlImpl implements FavoritesDao {

	public void addFavoriteList(long userId, long movieId) throws SystemException {

		Connection connection = null;
		PreparedStatement prepareStatement = null;

		int newUserId = (int) userId;
		int newMovieId = (int) movieId;

		try {

			connection = ConnectionHandler.getConnection();

			String addQuery = "insert into `movie_cruiser`.`favorites` (fs_us_id, fs_mo_id)  values(?, ?)";

			prepareStatement = connection.prepareStatement(addQuery);
			prepareStatement.setInt(1, newUserId);
			prepareStatement.setInt(2, newMovieId);
			prepareStatement.executeUpdate();

		} catch (SQLException sqlException) {

			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());

		} finally {

			try {

				if (prepareStatement != null) {
					prepareStatement.close();
				}

				if (connection != null) {
					connection.close();
				}

			} catch (SQLException sqlException) {

				throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			}

		}
	}

	public Favorites getAllFavoriteList(long userId) throws FavoriteEmptyException, SystemException {

		Connection connection = null;

		PreparedStatement preparedStatementFavorites = null;

		ResultSet resultSetFavorites = null;

		int newUserId = (int) userId;
		int noOfFavoritesCount = 0;
		Favorites favorites = null;

		try {

			connection = ConnectionHandler.getConnection();
			favorites = new Favorites(new ArrayList<Movies>(), 0);

			String favoriteQuery = "select distinct mo_id, mo_title, mo_box_office, mo_active, mo_date_of_launch, mo_genre, mo_has_teaser \r\n"
					+ "    from `movie_cruiser`.`favorites`\r\n" + "    inner join `movie_cruiser`.`movie_list` \r\n"
					+ "    on fs_mo_id = mo_id \r\n" + "    where fs_us_id = ?";

			preparedStatementFavorites = connection.prepareStatement(favoriteQuery);
			preparedStatementFavorites.setInt(1, newUserId);
			resultSetFavorites = preparedStatementFavorites.executeQuery();
			
			if (resultSetFavorites.next() == false) {
				
				throw new FavoriteEmptyException();
				
			} else {

				do {

					int id = resultSetFavorites.getInt(1);
					long newMovieId = (int) id;

					String movieActive = resultSetFavorites.getString(4);

					boolean active;
					if (movieActive.equals("Yes")) {
						active = true;
					} else {
						active = false;
					}

					String movieHasTeaser = resultSetFavorites.getString(7);
					boolean hasTeaser;
					if (movieHasTeaser.equals("Yes")) {
						hasTeaser = true;
					} else {
						hasTeaser = false;
					}

					Date date = resultSetFavorites.getDate(5);

					Movies movie = new Movies(newMovieId, resultSetFavorites.getString(2),
							resultSetFavorites.getLong(3), active, date, resultSetFavorites.getString(6), hasTeaser);
					favorites.getMovieList().add(movie);
					noOfFavoritesCount++;

				} while (resultSetFavorites.next());

				favorites.setNoOfFavorites(noOfFavoritesCount);

			}
		} catch (SQLException sqlException) {

			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());

		} finally {

			try {

				if (resultSetFavorites != null) {
					resultSetFavorites.close();
				}

				if (preparedStatementFavorites != null) {
					preparedStatementFavorites.close();
				}

				if (connection != null) {
					connection.close();
				}

			} catch (SQLException sqlException) {

				throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			}

		}
		return favorites;
	}

	public void removeFavoriteList(long userId, long movieId) throws SystemException {

		Connection connection = null;
		PreparedStatement prepareStatement = null;

		int newUserId = (int) userId;
		int newMovieId = (int) movieId;

		try {

			connection = ConnectionHandler.getConnection();

			String deleteQuery = "delete from `movie_cruiser`.`favorites` \r\n" + "    where fs_us_id = ? \r\n"
					+ "    and fs_mo_id = ?";
			prepareStatement = connection.prepareStatement(deleteQuery);
			prepareStatement.setInt(1, newUserId);
			prepareStatement.setInt(2, newMovieId);
			prepareStatement.executeUpdate();

		} catch (SQLException sqlException) {

			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());

		} finally {

			try {

				if (prepareStatement != null) {
					prepareStatement.close();
				}

				if (connection != null) {
					connection.close();
				}

			} catch (SQLException sqlException) {

				throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			}

		}
	}

}
