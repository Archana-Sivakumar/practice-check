package com.cognizant.moviecruiser.servlet;

import java.io.IOException;
import java.util.List;

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


@WebServlet("/ShowEditMovie")
public class ShowEditMovieServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long newMovieId = Long.parseLong(request.getParameter("movieId"));

		RequestDispatcher requestDispatcher = null;
		
		MoviesDao movies = new MoviesDaoSqlImpl();
		
		try {
			
			Movies singlemovie = movies.getMovieList(newMovieId);
			List<Movies> movie = movies.getMovieListAdmin();

			requestDispatcher = request.getRequestDispatcher("edit-movie.jsp");
			request.setAttribute("editMovie", singlemovie);
			request.setAttribute("editMovieList", movie);
			
		} catch (SystemException systemException) {

			String errorMsg = systemException.getMessage();
			requestDispatcher = request.getRequestDispatcher("edit-movie.jsp");
			request.setAttribute("connectionError", errorMsg);
		}
		
		requestDispatcher.forward(request, response);
	}

}
