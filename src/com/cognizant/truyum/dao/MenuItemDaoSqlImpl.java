package com.cognizant.truyum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.cognizant.truyum.model.MenuItem;

public class MenuItemDaoSqlImpl implements MenuItemDao {

	public List<MenuItem> getMenuItemListAdmin() throws SystemException {

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<MenuItem> menuAdminList = new ArrayList<MenuItem>();

		try {
			
			conn = ConnectionHandler.getConnection();

			String query = "select me_id, me_name, me_price, me_active, me_date_of_launch, me_category, me_free_delivery "
					        + " from `truyum`.`menu_item`";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
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

				MenuItem menuItem = new MenuItem(menuId, rs.getString(2), rs.getFloat(3), active, date, rs.getString(6),
						freeDelivery);
				menuAdminList.add(menuItem);
			}

		} catch (SQLException sqlException) {

			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());

		} finally {

			try {

				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}

			}catch (SQLException sqlException) {

				throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			}

		}
		return menuAdminList;
	}

	public List<MenuItem> getMenuItemListCustomer() throws SystemException {

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<MenuItem> menuCustomerList = new ArrayList<MenuItem>();

		try {
			
			conn = ConnectionHandler.getConnection();

			String query = "select me_id, me_name, me_price, me_active, me_date_of_launch, me_category, me_free_delivery "
					        + "from `truyum`.`menu_item`"
					        + "where me_active = 'Yes' and  me_date_of_launch < NOW() or me_date_of_launch = NOW()";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

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

				MenuItem menuItem = new MenuItem(menuId, rs.getString(2), rs.getFloat(3), active, date, rs.getString(6),
						freeDelivery);
				menuCustomerList.add(menuItem);
			}

		} catch (SQLException sqlException) {

			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());

		} finally {

			try {

				if (rs != null) {
					rs.close();
				}

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
		return menuCustomerList;
	}

	public MenuItem getMenuItem(long menuItemId) throws SystemException {

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		MenuItem menuItem = null;
		int newMenuId = (int) menuItemId;

		try {
			
			conn = ConnectionHandler.getConnection();

			String query = "select me_name, me_price, me_active, me_date_of_launch, me_category, me_free_delivery  "
					       + "from `truyum`.`menu_item` where me_id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, newMenuId);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				String menuActive = rs.getString(3);

				boolean active;
				if (menuActive.equals("Yes")) {
					active = true;
				} else {
					active = false;
				}

				String menuFreeDelivery = rs.getString(6);
				boolean freeDelivery;
				if (menuFreeDelivery.equals("Yes")) {
					freeDelivery = true;
				} else {
					freeDelivery = false;
				}

				Date date = rs.getDate(4);

				menuItem = new MenuItem(menuItemId, rs.getString(1), rs.getFloat(2), active, date, rs.getString(5),
						freeDelivery);

			}

		} catch (SQLException sqlException) {

			throw new SystemException(sqlException.getMessage(), sqlException.getErrorCode());
			
		} finally {
			
			try {

				if (rs != null) {
					rs.close();
				}
				
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
		return menuItem;
	}

	public void editMenuItem(MenuItem menuItem) throws SystemException {

		PreparedStatement pstmt = null;
		Connection conn = null;

		Date menuDate = menuItem.getDateOfLaunch();
		java.sql.Date sqlDate = new java.sql.Date(menuDate.getTime());

		String menuActive = " ";
		boolean active = menuItem.isActive();
		if (active == true) {
			menuActive = "Yes";
		} else {
			menuActive = "No";
		}

		String menuFreeDelivery = " ";
		boolean freeDelivery = menuItem.isFreeDelivery();
		if (freeDelivery == true) {
			menuFreeDelivery = "Yes";
		} else {
			menuFreeDelivery = "No";
		}

		int menuId = (int) menuItem.getId();

		try {
			
			conn = ConnectionHandler.getConnection();

			String query = "update `truyum`.`menu_item`"
					      + "set me_name = ?, me_price = ?, me_active = ?, me_date_of_launch = ?, me_category = ?, me_free_delivery = ?"
					      + "where me_id = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, menuItem.getName());
			pstmt.setFloat(2, menuItem.getPrice());
			pstmt.setString(3, menuActive);
			pstmt.setDate(4, sqlDate);
			pstmt.setString(5, menuItem.getCategory());
			pstmt.setString(6, menuFreeDelivery);
			pstmt.setInt(7, menuId);
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
