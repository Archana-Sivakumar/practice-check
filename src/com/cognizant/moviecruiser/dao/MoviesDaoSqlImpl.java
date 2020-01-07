package com.cognizant.moviecruiser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cognizant.moviecruiser.model.Movies;

public class MoviesDaoSqlImpl implements MoviesDao{

	public List<Movies> getMovieListAdmin() throws SystemException{
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		List<Movies> movieList = new ArrayList<Movies>();
		
		try {
			
			connection = ConnectionHandler.getConnection();

			String query = "select mo_id, mo_title, mo_box_office, mo_active, mo_date_of_launch, mo_genre, mo_has_teaser \r\n" + 
					"    from `movie_cruiser`.`movie_list`";
			
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {

				int id = resultSet.getInt(1);
				long newMovieId = Long.valueOf(id);

				String movieActive = resultSet.getString(4);

				boolean active;
				if (movieActive.equals("Yes")) {
					active = true;
				} else {
					active = false;
				}

				String movieHasTeaser = resultSet.getString(7);
				boolean hasTeaser;
				if (movieHasTeaser.equals("Yes")) {
					hasTeaser = true;
				} else {
					hasTeaser = false;
				}

				Date date = resultSet.getDate(5);

				Movies movie = new Movies(newMovieId, resultSet.getString(2), resultSet.getLong(3), active, date, resultSet.getString(6),
						hasTeaser);
				movieList.add(movie);
				
			}
			
		} catch (SQLException sqlException) {
			
			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			
		}  finally {
			
			try {
				
				if (resultSet != null) {
					resultSet.close();
				}

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
		return movieList;
	}

	public List<Movies> getMovieListCustomer() throws SystemException {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		List<Movies> movieListCustomer = new ArrayList<Movies>();
		
		try {
			
			connection = ConnectionHandler.getConnection();

			String query = "select mo_id, mo_title, mo_box_office, mo_active, mo_date_of_launch, mo_genre, mo_has_teaser \r\n" + 
					"    from `movie_cruiser`.`movie_list` \r\n" + 
					"    where mo_date_of_launch = NOW() \r\n" + 
					"    or mo_date_of_launch < NOW() \r\n" + 
					"    and mo_active = 'Yes'";
			
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt(1);
				long newMovieId = Long.valueOf(id);

				String movieActive = resultSet.getString(4);
				boolean active;
				if (movieActive.equals("Yes")) {
					active = true;
				} else {
					active = false;
				}

				Date date = resultSet.getDate(5);

				String movieHasTeaser = resultSet.getString(7);
				boolean hasTeaser;
				if (movieHasTeaser.equals("Yes")) {
					hasTeaser = true;
				} else {
					hasTeaser = false;
				}

				Movies movie = new Movies(newMovieId, resultSet.getString(2), resultSet.getLong(3), active, date, resultSet.getString(6),
						hasTeaser);
				movieListCustomer.add(movie);
			}

		} catch (SQLException sqlException) {
			
			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			
		} finally {
			
			try {
				
				if (resultSet != null) {
					resultSet.close();
				}

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
		return movieListCustomer;
	}

	public Movies getMovieList(long movieId) throws SystemException {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Movies movie = null;
		
		int newMovieId = (int) movieId;
		
		try {
			
			connection = ConnectionHandler.getConnection();
			
			String query = "select mo_title, mo_box_office, mo_active, mo_date_of_launch, mo_genre, mo_has_teaser \r\n"
					+ "from `movie_cruiser`.`movie_list` where mo_id = ?";
			
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1, newMovieId);
			resultSet = prepareStatement.executeQuery();

			resultSet.next();

			String movieActive = resultSet.getString(3);
			boolean active;
			if (movieActive.equals("Yes")) {
				active = true;
			} else {
				active = false;
			}

			Date date = resultSet.getDate(4);

			String movieHasTeaser = resultSet.getString(6);
			boolean hasTeaser;
			if (movieHasTeaser.equals("Yes")) {
				hasTeaser = true;
			} else {
				hasTeaser = false;
			}

			movie = new Movies(movieId, resultSet.getString(1), resultSet.getLong(2), active, date, resultSet.getString(5), hasTeaser);

		} catch (SQLException sqlException) {
			
			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			
		}  finally {
			
			try {
				
				if (resultSet != null) {
					resultSet.close();
				}

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

		return movie;
	}

	public void editMovieList(Movies movie) throws SystemException {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		
		int newMovieId = (int) movie.getId();

		String movieActive = " ";
		boolean active = movie.isActive();
		if (active == true) {
			movieActive = "Yes";
		} else {
			movieActive = "No";
		}

		Date movieDate = movie.getDateOfLaunch();
		java.sql.Date sqlDate = new java.sql.Date(movieDate.getTime());

		String movieHasTeaser = " ";
		boolean hasTeaser = movie.isHasTeaser();
		if (hasTeaser == true) {
			movieHasTeaser = "Yes";
		} else {
			movieHasTeaser = "No";
		}

		try {

			connection = ConnectionHandler.getConnection();
			
			String query = "update `movie_cruiser`.`movie_list`"
					+ "set mo_title = ?, mo_box_office = ?, mo_active = ?, mo_date_of_launch = ?, mo_genre = ?, mo_has_teaser = ?"
					+ "where mo_id = ?";
			
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, movie.getTitle());
			prepareStatement.setLong(2, movie.getBoxOffice());
			prepareStatement.setString(3, movieActive);
			prepareStatement.setDate(4, sqlDate);
			prepareStatement.setString(5, movie.getGenre());
			prepareStatement.setString(6, movieHasTeaser);
			prepareStatement.setInt(7, newMovieId);
			prepareStatement.executeUpdate();

		} catch (SQLException sqlException) {
			
			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			
		}  finally {
			
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