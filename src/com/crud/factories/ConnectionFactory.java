package com.crud.factories;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	public static Connection connection;
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "servlet", "password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		return connection;
	}
}
