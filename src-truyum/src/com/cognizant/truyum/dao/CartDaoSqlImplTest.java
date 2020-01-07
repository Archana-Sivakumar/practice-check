package com.cognizant.truyum.dao;

import java.util.List;

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;

public class CartDaoSqlImplTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testAddCartItem();
		testRemoveCartItem();
	}

		public static void testAddCartItem() {
			CartDao cartDao = new CartDaoSqlImpl ();
			System.out.println("Add the menu items into the cart");
			System.out.println("----------------------------------");
			
			System.out.println();
		
		try {
			cartDao.addCartItem(1, 3);
			cartDao.addCartItem(1, 1);
			cartDao.addCartItem(1, 4);
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

	public static void testGetAllCartItems() {
		CartDao cartDao = new CartDaoSqlImpl();
		
		try {
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
	public static void testRemoveCartItem() {
		CartDao cartDao = new CartDaoSqlImpl();
		System.out.println("Remove the menu items from the cart");
		System.out.println("----------------------------------");
	    
		System.out.println("Display cart items after removing from cart");
		System.out.println("----------------------------------");
		try {
			cartDao.removeCartItem(1, 3);
			cartDao.removeCartItem(1, 4);
			
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