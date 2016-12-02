package com.highscore;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Will be used for highscore database connection.

public class DatabaseConnector
{
	private Connection m_Connection;
	
	public DatabaseConnector()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
		} 
		catch (ClassNotFoundException e)
		{
			System.out.println(e.toString() + " not found.");
		}
		
		try 
		{
			// TODO: Remove hard coding
			m_Connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/prjo", "postgres",
					"#Aptur9191!");

		} 
		catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}	
	}
	
	public boolean sendHighscore(String name, int score)
	{
		boolean sentHighscore = true;
		try
		{
			if (m_Connection != null)
			{
				PreparedStatement statement = m_Connection.prepareStatement("INSERT INTO highscore (name, score) VALUES (?, ?);");
				statement.setString(1, name);
				statement.setInt(2, score);
				statement.executeUpdate();
			}
		} 
		catch (Exception e)
		{
			sentHighscore = false;
			System.out.println("Could not send highscore");
			e.printStackTrace();
		}
		
		return sentHighscore;
	}
	
}
