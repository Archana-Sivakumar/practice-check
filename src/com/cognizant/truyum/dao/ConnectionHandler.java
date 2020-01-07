package com.cognizant.truyum.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionHandler {
    static String driver;
    static String connection_url;
    static String user;
    static String password;
    
    static {
    	
		try {

			Properties  properties = new Properties();  
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("connection.properties"));
			driver = properties.getProperty("driver");
		    connection_url = properties.getProperty("connection-url");
		    user = properties.getProperty("user");  
		    password = properties.getProperty("password"); 
		    
		    Class.forName(driver);
		    
		} catch (IOException ioException) {
			
			ioException.printStackTrace();
			
       } catch (ClassNotFoundException classNotFound) {
    	   
			classNotFound.printStackTrace();
		}
    }
    
    static Connection getConnection() throws SQLException{
    	Connection connection = DriverManager.getConnection(connection_url, user, password);  
        return connection;
    
}
}
