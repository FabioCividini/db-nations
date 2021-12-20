package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
	
	private final static String URL = "jdbc:mysql://localhost:3306/db-nations";
	private final static String USER = "root";
	private final static String PASSWORD = "rootpassword";
	
	public static void main(String[] args) {
		
		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD)){
			
			String sql = "select c.name as name_countries, c.country_id as ID_countries, r.name as name_region, c2.name as name_continent from countries c \r\n"
					+ "join regions r on c.region_id = r.region_id\r\n"
					+ "join continents c2 on c2.continent_id = r.continent_id\r\n"
					+ "order by c.name;";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				
				try(ResultSet rs = ps.executeQuery()){
					
					while(rs.next()) {
						System.out.print(rs.getString(1) + " - ");
						System.out.print(rs.getInt(2) + " - ");
						System.out.print(rs.getString(3) + " - ");
						System.out.println(rs.getString(4));
					}
				}
				
			}
			
		} catch(SQLException se) {
			System.out.println("ERRORE: " + se.getMessage());
		}

	}

}
