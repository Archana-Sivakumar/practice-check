package com.cognizant.moviecruiser.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cognizant.moviecruiser.dao.FavoritesDao;
import com.cognizant.moviecruiser.dao.FavoritesDaoSqlImpl;
import com.cognizant.moviecruiser.dao.MoviesDao;
import com.cognizant.moviecruiser.dao.MoviesDaoSqlImpl;
import com.cognizant.moviecruiser.dao.SystemException;
import com.cognizant.moviecruiser.model.Movies;

@WebServlet("/AddToFavorite")
public class AddToFavoriteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long newId = Long.parseLong(request.getParameter("movieId"));

		FavoritesDao favoriteDao = new FavoritesDaoSqlImpl();
		
		RequestDispatcher requestDispatcher = null;
		
	    try {
	    	
			favoriteDao.addFavoriteList(1, newId);
			MoviesDao moviesDao = new MoviesDaoSqlImpl();
			List<Movies> movieList= moviesDao.getMovieListCustomer();
			

			requestDispatcher = request.getRequestDispatcher("movie-list-customer.jsp");
			request.setAttribute("addMovieList", movieList);
			request.setAttribute("addFavoriteStatus", true);
			
		} catch (SystemException systemException) {
			
			String errorMsg = systemException.getMessage();
			requestDispatcher = request.getRequestDispatcher("movie-list-customer.jsp");
			request.setAttribute("connectionError", errorMsg);
		}
	
	    requestDispatcher.forward(request, response);
	}

}
