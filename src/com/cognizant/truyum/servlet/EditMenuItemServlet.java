package com.cognizant.truyum.servlet;

import java.io.IOException;
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
import com.cognizant.truyum.util.DateUtil;

@WebServlet("/EditMenuItem")
public class EditMenuItemServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long newId = Long.parseLong(request.getParameter("menuItemId"));

		String name = request.getParameter("title");

		Float newPrice = Float.parseFloat(request.getParameter("price"));

		boolean newActive = Boolean.parseBoolean(request.getParameter("inStock"));

		String sdate = request.getParameter("dateOfLaunch");

		String category = request.getParameter("category");

		boolean newFreeDelivery = Boolean.parseBoolean(request.getParameter("freeDelivery"));

		MenuItem menuItem = new MenuItem(newId, name, newPrice, newActive, DateUtil.convertToDate(sdate), category,
				newFreeDelivery);
		
		RequestDispatcher rd = null;
		MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
		
		try {
			
			menuItemDao.editMenuItem(menuItem);
			rd = request.getRequestDispatcher("edit-menu-item-status.jsp");
			
		} catch (SystemException systemException) {
			
			String errorMsg = systemException.getMessage();
			rd = request.getRequestDispatcher("edit-menu-item.jsp");
			request.setAttribute("connectionError", errorMsg);
			
		}

		rd.forward(request, response);
	}

}
