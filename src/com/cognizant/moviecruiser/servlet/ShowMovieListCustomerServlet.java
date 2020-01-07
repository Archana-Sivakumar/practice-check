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

@WebServlet("/ShowMovieListCustomer")
public class ShowMovieListCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher requestDispatcher = null;

		MoviesDao movieDao = new MoviesDaoSqlImpl();
		
		try {
			
			List<Movies>  movies = movieDao.getMovieListCustomer();
			requestDispatcher = request.getRequestDispatcher("movie-list-customer.jsp");
			request.setAttribute("movieList", movies);
			
		} catch (SystemException systemException) {

			String errorMsg = systemException.getMessage();
			requestDispatcher = request.getRequestDispatcher("movie-list-customer.jsp");
			request.setAttribute("connectionError", errorMsg);
		}

		
		requestDispatcher.forward(request, response);

	}

}
