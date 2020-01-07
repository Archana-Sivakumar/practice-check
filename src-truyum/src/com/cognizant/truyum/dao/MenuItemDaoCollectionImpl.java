package com.cognizant.truyum.dao;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.util.DateUtil;

public class MenuItemDaoCollectionImpl implements MenuItemDao {
	private static List<MenuItem> menuItemList;

	public MenuItemDaoCollectionImpl() {
		if (menuItemList == null) {
			menuItemList = new ArrayList<MenuItem>();
			menuItemList.add(new MenuItem(1, "Sandwich", 99.00f, true, DateUtil.convertToDate("15/03/2017"),
					"MainCourse", true));
			menuItemList.add(new MenuItem(2, "Burger", 129.00f, true, DateUtil.convertToDate("23/12/2017"),
					"MainCourse", false));
			menuItemList.add(
					new MenuItem(3, "Pizza", 149.00f, true, DateUtil.convertToDate("21/08/2018"), "MainCourse", false));
			menuItemList.add(new MenuItem(4, "French Fries", 57.00f, false, DateUtil.convertToDate("02/07/2017"),
					"Starters", true));
			menuItemList.add(new MenuItem(5, "Chocolate Brownie", 32.00f, true, DateUtil.convertToDate("02/11/2022"),
					"Dessert", true));
		}
	}

	public static List<MenuItem> getMenuItemList() {
		return menuItemList;
	}

	public static void setMenuItemList(List<MenuItem> menuItemList) {
		MenuItemDaoCollectionImpl.menuItemList = menuItemList;
	}

	@Override
	public List<MenuItem> getMenuItemListAdmin() {
		return menuItemList;
	}

	@Override
	public List<MenuItem> getMenuItemListCustomer() {
		List<MenuItem> filteredArrayList = new ArrayList<MenuItem>();
		for (MenuItem item : menuItemList) {
			int dateCompare = item.getDateOfLaunch().compareTo(new Date());
			if (item.isActive() == true && (dateCompare == 0 || dateCompare < 0)) {
				filteredArrayList.add(item);
			}
		}
		return filteredArrayList;
	}

	@Override
	public void editMenuItem(MenuItem menuItem) {
		int i;
		for (i = 0; i < menuItemList.size(); i++) {
			if (menuItem.getId() == menuItemList.get(i).getId()) {
				menuItemList.set(i, menuItem);
				break;
			}
		}
	}

	@Override
	public MenuItem getMenuItem(long menuItemId) {
		int i;
		MenuItem menuItem = null;
		for (i = 0; i < menuItemList.size(); i++) {
			if (menuItemId == menuItemList.get(i).getId()) {
				menuItem = menuItemList.get(i);
				break;
			}
		}
		return menuItem;
	}

}
