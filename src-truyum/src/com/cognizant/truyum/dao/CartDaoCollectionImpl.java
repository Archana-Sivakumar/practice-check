package com.cognizant.truyum.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;

public class CartDaoCollectionImpl implements CartDao {

	private static Map<Long, Cart> userCarts;

	public CartDaoCollectionImpl() {

		if (userCarts == null) {
			userCarts = new HashMap<Long, Cart>();
		}
	}

	public static Map<Long, Cart> getUserCarts() {

		return userCarts;
	}

	public static void setUserCarts(HashMap<Long, Cart> userCarts) {

		CartDaoCollectionImpl.userCarts = userCarts;
	}

	@Override
	public void addCartItem(long userId, long menuItemID) throws SystemException {

		MenuItemDao menuItemDao = new MenuItemDaoCollectionImpl();
		MenuItem menuItem = menuItemDao.getMenuItem(menuItemID);

		if (userCarts.containsKey(userId)) {
			
			Cart cartItem = userCarts.get(userId);
			cartItem.getMenuItemList().add(menuItem);
			
		} else {
			
			Cart cartItem = new Cart(new ArrayList<MenuItem>(), 0.0);
			cartItem.getMenuItemList().add(menuItem);
			userCarts.put(userId, cartItem);
		}
	}

	@Override
	public Cart getAllCartItems(long userId) throws CartEmptyException {

		if (userCarts.isEmpty()) {
			
			CartEmptyException cartEmpty = new CartEmptyException();
			throw cartEmpty;
			
		} else {
			
			double total = 0.0;
			Cart cartItem = userCarts.get(userId);
			List<MenuItem> menuItem = cartItem.getMenuItemList();

			if (menuItem.isEmpty()) {
				
				CartEmptyException cartEmpty = new CartEmptyException();
				throw cartEmpty;
				
			} else {
				
				for (int i = 0; i < menuItem.size(); i++) {
					total += menuItem.get(i).getPrice();
				}
				cartItem.setTotal(total);
				cartItem.setMenuItemList(menuItem);
			}

			return cartItem;
		}
	}

	@Override
	public void removeCartItem(long userId, long menuItemId) {

		Cart cartItem = userCarts.get(userId);
		List<MenuItem> menuItem = cartItem.getMenuItemList();

		for (int i = 0; i < menuItem.size(); i++) {
			
			if (menuItem.get(i).getId() == menuItemId) {
				menuItem.remove(i);
				break;
			}
		}

	}

}
