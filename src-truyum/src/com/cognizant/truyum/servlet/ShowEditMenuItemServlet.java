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

@WebServlet("/ShowEditMenuItem")
public class ShowEditMenuItemServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long newId = Long.parseLong(request.getParameter("menuItemId"));

		MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
		RequestDispatcher rd = null;
		
		try {
			
			List<MenuItem> menuItemList = menuItemDao.getMenuItemListAdmin();
			MenuItem menu = menuItemDao.getMenuItem(newId);

			rd = request.getRequestDispatcher("edit-menu-item.jsp");
			request.setAttribute("menuItemEdit", menu);
			request.setAttribute("menuList", menuItemList);
			
		} catch (SystemException systemException) {

			String errorMsg = systemException.getMessage();
			rd = request.getRequestDispatcher("edit-menu-item.jsp");
			request.setAttribute("connectionError", errorMsg);
		}
		
		rd.forward(request, response);
	}

}
