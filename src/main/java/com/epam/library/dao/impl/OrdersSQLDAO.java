/**
 * 
 */
package com.epam.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.library.beans.AccessLevel;
import com.epam.library.beans.Book;
import com.epam.library.beans.BookStatus;
import com.epam.library.beans.OrderStatus;
import com.epam.library.beans.Orders;
import com.epam.library.beans.User;
import com.epam.library.dao.connection.ConnectionSQLDAO;
import com.epam.library.dao.connection.ConnectionSQLException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.interfaces.OrdersDAO;

/**
 * @author Eugene13
 *
 */
public class OrdersSQLDAO implements OrdersDAO {
	private final static String GET_ALL_ORDERS = "SELECT " + "order_id, order_status.order_status_id, order_status, "
			+ "user.user_id, name, second_name, login, acc_level, "
			+ "book.book_id, book_name, author, book_status_id " + "FROM " + "orders " + "LEFT JOIN "
			+ "order_status ON orders.order_status_id = order_status.order_status_id " + "LEFT JOIN "
			+ "user ON orders.user_id=user.user_id " + "LEFT JOIN " + "book ON orders.book_id=book.book_id;";
	private final static String ORDER_ADD = "INSERT INTO orders (`book_id`, `user_id`, `order_status_id`) VALUES(?,?,1)";
	private final static String SEND_ORDER = "UPDATE orders SET order_status_id = 2 WHERE order_id = ?";
	private final static String RETURN_ORDER = "UPDATE orders SET order_status_id = 3 WHERE order_id = ?";

	private final static String ORDER_ID = "order_id";
	private final static String ORDER_STATUS_ID = "order_status_id";
	private final static String ORDER_STATUS_NAME = "order_status";
	private final static String ORDER_USER_ID = "user_id";
	private final static String ORDER_USER_NAME = "name";
	private final static String ORDER_USER_SECOND_NAME = "second_name";
	private final static String ORDER_USER_LOGIN = "login";
	private final static String ORDER_USER_ACCESS_LEVEL = "acc_level";
	private final static String ORDER_BOOK_ID = "book_id";
	private final static String ORDER_BOOK_NAME = "book_name";
	private final static String ORDER_BOOK_AUTHOR = "author";
	private final static String ORDER_BOOK_STATUS_ID = "book_status_id";

	private static final OrdersDAO instance = new OrdersSQLDAO();

	private OrdersSQLDAO() {
	}

	public static OrdersDAO getInstance() {
		return instance;
	}

	@Override
	public ArrayList<Orders> getAllOrders() throws DAOException {
		Connection connection = null;
		PreparedStatement pSt = null;
		ResultSet rs = null;
		try {
			connection = ConnectionSQLDAO.getInstance().takeConnection();
			pSt = connection.prepareStatement(GET_ALL_ORDERS);
			rs = pSt.executeQuery();
			Orders localOrder;
			ArrayList<Orders> orders = new ArrayList<>();
			while (rs.next()) {
				localOrder = new Orders();
				localOrder.setOrderId(rs.getInt(ORDER_ID));
				OrderStatus orderStatus = new OrderStatus();
				orderStatus.setOrderStatusId(rs.getInt(ORDER_STATUS_ID));
				orderStatus.setOrderStatus(rs.getString(ORDER_STATUS_NAME));
				localOrder.setOrderStatus(orderStatus);
				User orderUser = new User();
				orderUser.setUserId(rs.getInt(ORDER_USER_ID));
				orderUser.setName(rs.getString(ORDER_USER_NAME));
				orderUser.setSecondName(rs.getString(ORDER_USER_SECOND_NAME));
				orderUser.setLogin(rs.getString(ORDER_USER_LOGIN));
				AccessLevel accessLevel = new AccessLevel();
				accessLevel.setAccessLevelId(rs.getInt(ORDER_USER_ACCESS_LEVEL));
				orderUser.setAccessLevel(accessLevel);
				localOrder.setUser(orderUser);
				Book orderBook = new Book();
				orderBook.setBookId(rs.getInt(ORDER_BOOK_ID));
				orderBook.setBookName(rs.getString(ORDER_BOOK_NAME));
				orderBook.setAuthor(rs.getString(ORDER_BOOK_AUTHOR));
				BookStatus bookStatus = new BookStatus();
				bookStatus.setBookStatusId(rs.getInt(ORDER_BOOK_STATUS_ID));
				orderBook.setBookStatus(bookStatus);
				localOrder.setBook(orderBook);
				orders.add(localOrder);
			}
			return orders;
		} catch (SQLException e) {
			throw new DAOException("Get list of orders sql exception.", e);
		} catch (ConnectionSQLException e) {
			throw new DAOException("Smthg wrong with connection.", e);
		}
	}

	@Override
	public void addOrder(int userId, int bookId) throws DAOException {
		Connection connection = null;
		PreparedStatement pSt = null;
		try {
			connection = ConnectionSQLDAO.getInstance().takeConnection();
			pSt = connection.prepareStatement(ORDER_ADD);
			pSt.setInt(1, bookId);
			pSt.setInt(2, userId);
			int i = pSt.executeUpdate();
			if (i > 0) {
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Add order sql exception.", e);
		} catch (ConnectionSQLException e) {
			throw new DAOException("Smthg wrong with connection.", e);
		}
	}

	@Override
	public void confirmOrder(int orderId) throws DAOException {
		Connection connection = null;
		PreparedStatement pSt = null;
		try {
			connection = ConnectionSQLDAO.getInstance().takeConnection();
			pSt = connection.prepareStatement(SEND_ORDER);
			pSt.setInt(1, orderId);
			int access = pSt.executeUpdate();
			if (access > 0) {
				return;
			}
		} catch (SQLException e) {
			throw new DAOException("Update order status sql exception.", e);
		} catch (ConnectionSQLException e) {
			throw new DAOException("Smthg wrong with connection.", e);
		}
	}

	@Override
	public void confirmReturn(int orderId) throws DAOException {
		Connection connection = null;
		PreparedStatement pSt = null;
		try {
			connection = ConnectionSQLDAO.getInstance().takeConnection();
			pSt = connection.prepareStatement(RETURN_ORDER);
			pSt.setInt(1, orderId);
			int access = pSt.executeUpdate();
			if (access > 0) {
				return;
			}
		} catch (SQLException e) {
			throw new DAOException("Update order status sql exception.", e);
		} catch (ConnectionSQLException e) {
			throw new DAOException("Smthg wrong with connection.", e);
		}
	}
}
