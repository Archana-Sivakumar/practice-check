package com.cognizant.truyum.dao;

import com.cognizant.truyum.model.MenuItem;
import java.util.List;

public interface MenuItemDao {
	public List<MenuItem> getMenuItemListAdmin() throws SystemException;

	public List<MenuItem> getMenuItemListCustomer() throws SystemException;

	public void editMenuItem(MenuItem menuItem) throws SystemException;

	public MenuItem getMenuItem(long menuItemId) throws SystemException;

}
