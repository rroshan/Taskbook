package com.taskbook.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*
 * Singleton Pattern
 * Eager Initialization of ConnectionFactory.
 */

public class ConnectionFactory {
	private static final ConnectionFactory instance = new ConnectionFactory();
	private static DataSource dataSource;

	private ConnectionFactory() {
		try {
			InitialContext context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/TestDB");

		} catch (NamingException ex) {
			ex.printStackTrace();
		}
	}

	private Connection createConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return conn;
	}   

	public static Connection getConnection() {
		return instance.createConnection();
	}

	public static void closeResources(ResultSet set, Statement statement, Connection conn) {
		if (set != null) {
			try {
				set.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
