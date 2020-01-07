package com.cognizant.truyum.dao;


import com.cognizant.truyum.model.Cart;

public interface CartDao {
	
	public void addCartItem(long userId, long menuItemID) throws SystemException;

	public Cart getAllCartItems(long userId) throws CartEmptyException, SystemException;

	public void removeCartItem(long userId, long menuItemId) throws SystemException;
}
