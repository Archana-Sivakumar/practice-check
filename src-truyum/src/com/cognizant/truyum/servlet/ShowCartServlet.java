package com.cognizant.truyum.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cognizant.truyum.dao.CartDao;
import com.cognizant.truyum.dao.CartDaoSqlImpl;
import com.cognizant.truyum.dao.CartEmptyException;
import com.cognizant.truyum.dao.SystemException;
import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;

@WebServlet("/ShowCart")
public class ShowCartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		RequestDispatcher requestDispatcher = null;
		
		try {
			
			CartDao cartItemDao = new CartDaoSqlImpl();
			Cart cartObj = cartItemDao.getAllCartItems(1);
			List<MenuItem> menuItem = cartObj.getMenuItemList();

			requestDispatcher = request.getRequestDispatcher("cart.jsp");
			request.setAttribute("productList", cartObj);
			request.setAttribute("menuList", menuItem);
		
		} catch (CartEmptyException cart) {
			
			requestDispatcher = request.getRequestDispatcher("cart-empty.jsp");
			
		} catch (SystemException systemException) {
			
			String errorMsg = systemException.getMessage();
			requestDispatcher = request.getRequestDispatcher("cart.jsp");
			request.setAttribute("connectionError", errorMsg);
			
		}
		
		requestDispatcher.forward(request, response);
	}

}
