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

@WebServlet("/ShowMenuItemListAdmin")
public class ShowMenuItemListAdminServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
		RequestDispatcher rd = null;
		
		try {
			
			List<MenuItem> menuItemList= menuItemDao.getMenuItemListAdmin();
			rd = request.getRequestDispatcher("menu-item-list-admin.jsp");
			request.setAttribute("menuItemAdmin", menuItemList);
			
		} catch (SystemException systemException) {
			
			String errorMsg = systemException.getMessage();
			rd = request.getRequestDispatcher("menu-item-list-admin.jsp");
			request.setAttribute("connectionError", errorMsg);
			
		}
	
		rd.forward(request, response);
	}

}
