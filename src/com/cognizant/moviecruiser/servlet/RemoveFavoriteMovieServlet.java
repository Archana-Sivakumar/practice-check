package com.cognizant.moviecruiser.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cognizant.moviecruiser.dao.FavoriteEmptyException;
import com.cognizant.moviecruiser.dao.FavoritesDao;
import com.cognizant.moviecruiser.dao.FavoritesDaoSqlImpl;
import com.cognizant.moviecruiser.dao.SystemException;
import com.cognizant.moviecruiser.model.Favorites;
import com.cognizant.moviecruiser.model.Movies;

@WebServlet("/RemoveFavoriteMovie")
public class RemoveFavoriteMovieServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long newId = Long.parseLong(request.getParameter("movieId"));

		RequestDispatcher requestDispatcher = null;
		
		FavoritesDao favoriteDao = new FavoritesDaoSqlImpl();
		
		try {
			
			favoriteDao.removeFavoriteList(1, newId);
			Favorites favorite = favoriteDao.getAllFavoriteList(1);
			List<Movies> movie = favorite.getMovieList();

			requestDispatcher = request.getRequestDispatcher("favorites.jsp");
			request.setAttribute("favoriteObj", favorite);
			request.setAttribute("movieList", movie);
			request.setAttribute("removeFavoriteStatus", true);
			
		} catch (FavoriteEmptyException favoriteException) {
			
			requestDispatcher = request.getRequestDispatcher("favorites-empty.jsp");
			
		} catch (SystemException systemException) {
			
			String errorMsg = systemException.getMessage();
			requestDispatcher = request.getRequestDispatcher("favorites.jsp");
			request.setAttribute("connectionError", errorMsg);
		}
	
		requestDispatcher.forward(request, response);
	}

}
