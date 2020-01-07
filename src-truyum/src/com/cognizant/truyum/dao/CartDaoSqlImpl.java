package com.cognizant.truyum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;

public class CartDaoSqlImpl implements CartDao {

	public void addCartItem(long userId, long menuItemId) throws SystemException {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		int newUserId = (int) userId;
		int newMenuId = (int) menuItemId;
		
		try {
			
			conn = ConnectionHandler.getConnection();
			
			String query = "insert into `truyum`.`cart` (ct_us_id, ct_pr_id) values(?, ?)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, newUserId);
			pstmt.setInt(2, newMenuId);
			pstmt.executeUpdate();
			
		} catch (SQLException sqlException) {

			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			
		}
		finally {
			
			try {
				
				if(pstmt != null){
					 pstmt.close();
				}
				
				if(conn!= null) {
				    conn.close();
				}
				
			} catch (SQLException sqlException) {

				throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			}
			
		}
	}

	@Override
	public Cart getAllCartItems(long userId) throws CartEmptyException, SystemException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement prstmt = null;
		ResultSet rs = null;
		ResultSet rset = null;
		ResultSet resultset = null;
		Cart cart = null;
		
		int newUserId = (int) userId;
		
		try {
			
			conn = ConnectionHandler.getConnection();
			cart = new Cart(new ArrayList<MenuItem>(), 0.0);
	
			String emptyQuery = "select count(ct_id) from `truyum`.`cart`";
			pstmt = conn.prepareStatement(emptyQuery);
			resultset = pstmt.executeQuery();
			
			resultset.next();
			String empty = resultset.getString(1);
			int value = Integer.parseInt(empty);
			
			if (value == 0) {
				
				throw new CartEmptyException();
				
			} else {
				
			    String query = "select me_id, me_name, me_price, me_active, me_date_of_launch, me_category, me_free_delivery "
					+ "from `truyum`.`cart` inner join `truyum`.`menu_item` on ct_pr_id = me_id where ct_us_id = ?";
			    
			    prstmt = conn.prepareStatement(query);
			    prstmt.setInt(1, newUserId);
			    rs = prstmt.executeQuery();
               
			    while (rs.next()) {
				
					int id = rs.getInt(1);
					long menuId = Long.valueOf(id);

					String menuActive = rs.getString(4);

					boolean active;
					if (menuActive.equals("Yes")) {
						active = true;
					} else {
						active = false;
					}

					String menuFreeDelivery = rs.getString(7);
					boolean freeDelivery;
					if (menuFreeDelivery.equals("Yes")) {
						freeDelivery = true;
					} else {
						freeDelivery = false;
					}

					Date date = rs.getDate(5);

					MenuItem menuItem = new MenuItem(menuId, rs.getString(2), rs.getFloat(3), active, date,
							rs.getString(6), freeDelivery);
					cart.getMenuItemList().add(menuItem);
			    
			    
					String priceQuery = "select sum(me_price) as total from `truyum`.`cart` "
							+ "inner join `truyum`.`menu_item` on ct_pr_id = me_id where ct_us_id = ?";
					
					pstmt = conn.prepareStatement(priceQuery);
					pstmt.setInt(1, newUserId);
					rset = pstmt.executeQuery();
					
					rset.next();
					String total = rset.getString(1);
					double newTotal = Double.parseDouble(total);
					cart.setTotal(newTotal);
			    }
		       }
			
		} catch (SQLException sqlException) {

			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			
		} finally {
			
			try {
				
				if(rs!= null) {
					rs.close();
				}
				
				if(rset!= null) {
					rset.close();
				}
				
				if(resultset!= null) {
					resultset.close();
				}
				
				if(pstmt!=null){
					   pstmt.close();
				}
				
				if(prstmt!=null){
					   prstmt.close();
				}
				
				if(conn!= null) {
					conn.close();
				}
				
		    } catch (SQLException sqlException) {

				throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			}
			
		}
		return cart;
	}

	@Override
	public void removeCartItem(long userId, long menuItemId) throws SystemException {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		int newUserId = (int) userId;
		int newMenuId = (int) menuItemId;

		try {
			
			conn = ConnectionHandler.getConnection();

			String userQuery = "delete from `truyum`.`cart` where ct_us_id = ? and ct_pr_id = ?";
			
			pstmt = conn.prepareStatement(userQuery);
			pstmt.setInt(1, newUserId);
			pstmt.setInt(2, newMenuId);
			pstmt.executeUpdate();

		} catch (SQLException sqlException) {

			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			
		} finally {

			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}

			} catch (SQLException sqlException) {

				throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			}

		}
	}
}
