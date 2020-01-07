package com.cognizant.truyum.dao;

import java.util.List;

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;

public class CartDaoCollectionImplTest {

	public static void main(String[] args) {

		testAddCartItem();

		testRemoveCartItem();
	}

	public static void testAddCartItem()  {
		
		CartDao cartDao = new CartDaoCollectionImpl();
		System.out.println("Add the menu items into the cart");
		System.out.println("----------------------------------");
		
		System.out.println("Display added cart items");
		System.out.println("----------------------------------");
		
		try {
			cartDao.addCartItem(1, 3);
			cartDao.addCartItem(1, 4);
			cartDao.addCartItem(1, 1);
			cartDao.addCartItem(1, 2);
			
			Cart cartObj = cartDao.getAllCartItems(1);
			List<MenuItem> menuItem = cartObj.getMenuItemList();
			
			for (MenuItem menu : menuItem) {
				System.out.println(menu);
				System.out.println();
			}
			
		} catch (CartEmptyException cartEmpty) {
			
			System.out.println(cartEmpty.getMessage());
			
		} catch (SystemException systemException) {
			
			System.out.println(systemException.getMessage());
		}

	}

	public static void testGetAllCartItems() throws SystemException {
		
		CartDao cartDao = new CartDaoCollectionImpl();
		
		try {
			
			Cart cartObj = cartDao.getAllCartItems(1);
			List<MenuItem> menuItem = cartObj.getMenuItemList();
			
			for (MenuItem menu : menuItem) {
				System.out.println(menu);
				System.out.println();
			}
		} catch (CartEmptyException cartEmpty) {
			
			System.out.println(cartEmpty.getMessage());
		}
	}

	public static void testRemoveCartItem() {
		
		CartDao cartDao = new CartDaoCollectionImpl();
		
	    try {
	    	
			cartDao.removeCartItem(1, 1);
			cartDao.removeCartItem(1, 4);
			
		} catch (SystemException systemException) {
			
			System.out.println(systemException.getMessage());
		}
		
		System.out.println("Display cart items after removing from cart");
		System.out.println("----------------------------------");
		
		try {
			
			Cart cartObj = cartDao.getAllCartItems(1);
			List<MenuItem> menuItem = cartObj.getMenuItemList();
			cartDao.getAllCartItems(1);
			
			for (MenuItem menu : menuItem) {
				System.out.println(menu);
				System.out.println();
			}
			
		} catch (CartEmptyException cartEmpty) {
			
			System.out.println(cartEmpty.getMessage());
			
		} catch (SystemException systemException) {
			
			System.out.println(systemException.getMessage());
		}
	}
}
