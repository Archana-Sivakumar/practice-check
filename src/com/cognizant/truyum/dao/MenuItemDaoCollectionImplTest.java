package com.cognizant.truyum.dao;

import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.util.DateUtil;

public class MenuItemDaoCollectionImplTest {

	public static void main(String[] args) {

		testGetMenuItemListAdmin();

		testGetMenuItemListCustomer();

		testEditMenuItem();

	}

	private static void testGetMenuItemListAdmin() {

		System.out.println("*****WELCOME*****");
		System.out.println("Admin menu item list");
		System.out.println("---------------------");

		MenuItemDao menuItemDao = new MenuItemDaoCollectionImpl();
		try {
			for (MenuItem item : menuItemDao.getMenuItemListAdmin()) {
				System.out.println(item);
				System.out.println();
			}
		} catch (SystemException systemException) {
			System.out.println(systemException.getMessage());
		}
	}

	private static void testGetMenuItemListCustomer() {

		System.out.println("Customer menu item list");
		System.out.println("------------------------");

		MenuItemDao menuItemDao = new MenuItemDaoCollectionImpl();
		try {
			for (MenuItem item : (menuItemDao.getMenuItemListCustomer())) {
				System.out.println(item);
				System.out.println();
			}
		} catch (SystemException systemException) {
			System.out.println(systemException.getMessage());
	}
	}
	private static void testEditMenuItem() {

		System.out.println("Modify the menu item");
		System.out.println("---------------------------");

		MenuItem item = new MenuItem(1, "Hot Dog", 99.00f, true, DateUtil.convertToDate("15/03/2017"), "MainCourse",
				true);
		MenuItemDao menuItemDao = new MenuItemDaoCollectionImpl();
		try {
			menuItemDao.editMenuItem(item);
			
			System.out.println("Display the modified menu item");
			System.out.println("------------------------------");
			
			item = menuItemDao.getMenuItem(1);
			System.out.println(item);
		} catch (SystemException systemException) {
			System.out.println(systemException.getMessage());		}

		

		
	}
}
