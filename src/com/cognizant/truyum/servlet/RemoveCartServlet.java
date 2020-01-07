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

@WebServlet("/RemoveCartItem")
public class RemoveCartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long newRemoveId = Long.parseLong(request.getParameter("menuItemId"));

		CartDao cartItemDao = new CartDaoSqlImpl();
		
		request.setAttribute("removeCartItemStatus", true);

		RequestDispatcher rd = null;
		
		try {
			
			cartItemDao.removeCartItem(1, newRemoveId);
			Cart cartObj = cartItemDao.getAllCartItems(1);
			List<MenuItem> menuItem = cartObj.getMenuItemList();

			rd = request.getRequestDispatcher("cart.jsp");
			request.setAttribute("productList", cartObj);
			request.setAttribute("menuList", menuItem);
			
		} catch (CartEmptyException cartException) {
			
			rd = request.getRequestDispatcher("cart-empty.jsp");
			
		} catch (SystemException systemException) {
			
			String errorMsg = systemException.getMessage();
			rd = request.getRequestDispatcher("cart.jsp");
			request.setAttribute("connectionError", errorMsg);
			
		}
		
		rd.forward(request, response);
	}
}
