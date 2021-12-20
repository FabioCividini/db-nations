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
		boolean valido = false;
		String tabella = "";
		
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
					
					tabella = String.format("%5s%50s%30s%30s","ID:", "NAME COUNTRY:", "REGION:", "CONTINENT:\r");
					while(rs.next()) {
						valido = true;
						tabella += String.format("%5s%50s%30s%30s",rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
					}
				}
				
			}
			if (valido) {
				System.out.println(tabella);
				System.out.print("Choose a country ID: ");
				String userId = scan.nextLine();
				
				String sql2 = "select c.name from countries c where c.country_id = ?;";
				try(PreparedStatement ps2 = con.prepareStatement(sql2)){
					ps2.setString(1, userId);
					
					try(ResultSet rs2 = ps2.executeQuery()){
						
						while(rs2.next()) {
							System.out.println("\nDetails for country: " + rs2.getString(1));
						}
					}
				}
				
				String sql3 = "select l.`language` from country_languages cl \r\n"
						+ "join languages l on l.language_id = cl.language_id\r\n"
						+ "where cl.country_id = ?;";
				try(PreparedStatement ps3 = con.prepareStatement(sql3)){
					ps3.setString(1, userId);
					
					try(ResultSet rs3 = ps3.executeQuery()){
						
						System.out.print("Languages: ");
						while(rs3.next()) {
							System.out.print(rs3.getString(1) + "  ");
						}
					}
				}
				
				String sql4 = "select `year`, population, gdp \r\n"
						+ "from country_stats cs \r\n"
						+ "where cs.country_id = ?\r\n"
						+ "order by `year` desc\r\n"
						+ "limit 1;";
				try(PreparedStatement ps4 = con.prepareStatement(sql4)){
					ps4.setString(1, userId);
					
					try(ResultSet rs4 = ps4.executeQuery()){
						
						System.out.println("\nMost recent stats");
						while(rs4.next()) {
							System.out.println("Year: " + rs4.getString(1));
							System.out.println("Population: " + rs4.getString(2));
							System.out.println("Gdp: " + rs4.getString(3));
						}
					}
				}
				
			} else {
				System.out.println("Non ci sono risultati");
			}
			
			
		} catch(SQLException se) {
			System.out.println("ERRORE: " + se.getMessage());
		}
		
		scan.close();
	}

}
