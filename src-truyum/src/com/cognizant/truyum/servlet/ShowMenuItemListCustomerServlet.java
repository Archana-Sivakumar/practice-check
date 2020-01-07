package com.cognizant.truyum.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cognizant.truyum.dao.MenuItemDao;
import com.cognizant.truyum.dao.MenuItemDaoSqlImpl;
import com.cognizant.truyum.dao.SystemException;
import com.cognizant.truyum.model.MenuItem;

@WebServlet("/ShowMenuItemListCustomer")
public class ShowMenuItemListCustomerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher rd = null;
		MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
		
		try {
			
			List<MenuItem> menuItemList = menuItemDao.getMenuItemListCustomer();
			rd = request.getRequestDispatcher("menu-item-list-customer.jsp");
			request.setAttribute("menuItemCustomer", menuItemList);
			
		} catch (SystemException systemException) {
			
			String errorMsg = systemException.getMessage();
			rd = request.getRequestDispatcher("menu-item-list-customer.jsp");
			request.setAttribute("connectionError", errorMsg);
			
		}

		rd.forward(request, response);
	}

}
