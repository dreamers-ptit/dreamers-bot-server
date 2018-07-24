package com.dreamers.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigurationDao {
	
	public ConfigurationDao() {
		// TODO Auto-generated constructor stub
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
//		Class.forName("org.postgresql.Driver");
//		return DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
		
		// only for local test
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/test");
	}
}
