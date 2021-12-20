package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	
	private final static String URL = "jdbc:mysql://localhost:3306/db-nations";
	private final static String USER = "root";
	private final static String PASSWORD = "rootpassword";
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD)){
			
			System.out.print("Search: ");
			String ricerca = scan.nextLine();
			
			String sql = "select c.country_id as ID_countries, c.name, r.name as name_region, c2.name as name_continent from countries c \r\n"
					+ "join regions r on c.region_id = r.region_id\r\n"
					+ "join continents c2 on c2.continent_id = r.continent_id\r\n"
					+ "where c.name like ?\r\n"
					+ "order by c.name;";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, "%" + ricerca + "%");
				
				try(ResultSet rs = ps.executeQuery()){
					
					System.out.format("%5s%50s%30s%30s","ID:", "NAME COUNTRIES:", "REGION:", "CONTINENT:\r");
					while(rs.next()) {
						
						System.out.format("%5s%50s%30s%30s",rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) + "\r");
					}
				}
				
			}
			
		} catch(SQLException se) {
			System.out.println("ERRORE: " + se.getMessage());
		}
		
		scan.close();
	}

}
