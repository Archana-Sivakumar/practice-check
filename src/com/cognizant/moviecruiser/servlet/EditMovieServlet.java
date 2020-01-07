package com.cognizant.moviecruiser.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cognizant.moviecruiser.dao.MoviesDao;
import com.cognizant.moviecruiser.dao.MoviesDaoSqlImpl;
import com.cognizant.moviecruiser.dao.SystemException;
import com.cognizant.moviecruiser.model.Movies;
import com.cognizant.moviecruiser.util.DateUtil;

@WebServlet("/EditMovie")
public class EditMovieServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher requestDispatcher = null;

		long newId = Long.parseLong(request.getParameter("movieId"));

		String name = request.getParameter("title");

		long newBoxOffice = Long.parseLong(request.getParameter("boxOffice"));
		
		boolean newActive = Boolean.parseBoolean(request.getParameter("inStock"));

		String newDate = request.getParameter("dateOfLaunch");

		String newGenre = request.getParameter("genre");
		
		boolean newHasTeaser;
		if(request.getParameter("hasTeaser") == null) {
			newHasTeaser = false;
		}
		else {
			newHasTeaser = true;
		}
		
		Movies movie = new Movies(newId, name, newBoxOffice, newActive, DateUtil.convertToDate(newDate), newGenre,
				newHasTeaser);

		MoviesDao movies = new MoviesDaoSqlImpl();
		
		try {
			
			movies.editMovieList(movie);
			requestDispatcher = request.getRequestDispatcher("edit-movie-status.jsp");
			
		} catch (SystemException systemException) {

			String errorMsg = systemException.getMessage();
			requestDispatcher = request.getRequestDispatcher("edit-movie.jsp");
			request.setAttribute("connectionError", errorMsg);
		}

		
		requestDispatcher.forward(request, response);

	}

}
