package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Database {

	private String userName;
	private String passWord;
	private String firstName;
	private String lastName;


	public Database(){
		try {
			createTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//tesr
			e.printStackTrace();
		}
	}

	public Database(String user,String pass,String first,String last){
		this.userName = user;
		this.passWord = pass;
		this.firstName = first;
		this.lastName = last;
	}

	public void createTable() throws Exception{
		try {
			String cinemas = "CREATE TABLE IF NOT EXISTS cinema(name varchar(255),x varchar(255),y varchar(255), PRIMARY KEY(name))";
			String movies = "CREATE TABLE IF NOT EXISTS movie(name varchar(255), rating varchar(255), PRIMARY KEY(name))";
			String showing = "CREATE TABLE IF NOT EXISTS showing(showtimeID int NOT NULL AUTO_INCREMENT, cinema varchar(255), movie varchar(255), showtime varchar(255),PRIMARY KEY(showtimeID))";
			
			Connection con = getConnection();
			PreparedStatement createCinemas = con.prepareStatement(cinemas);
			createCinemas.executeUpdate();
			
			//Connection con2 = getConnection();
			PreparedStatement createMovies = con.prepareStatement(movies);
			createMovies.executeUpdate();
			
			PreparedStatement createShowing = con.prepareStatement(showing);
			createShowing.executeUpdate();
			

		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public boolean insertMovie(String name, String rating) throws Exception{

		try {
			
			ResultSet rs1 = null;
			Connection con = getConnection();

			rs1 = con.createStatement().executeQuery("SELECT * FROM movie WHERE name = '"+name+"'");

			if(rs1.next()){
				return false;
			}
			String sql = "INSERT INTO movie(name,rating) VALUES ('"+name+"','"+rating+"')";
			PreparedStatement insert = con.prepareStatement(sql);
			insert.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	public boolean insertCinema(String name, String x, String y) throws Exception {
		try {
			ResultSet rs1 = null;
			Connection con = getConnection();

			rs1 = con.createStatement().executeQuery("SELECT * FROM cinema WHERE name = '"+name+"'");

			if(rs1.next()){
				return false;
			}
			
			String sql = "INSERT INTO cinema(name,x,y) VALUES ('"+name+"','"+x+"', '"+y+"')";
			PreparedStatement insert = con.prepareStatement(sql);
			insert.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean insertShowtime(String cinema, String movie, String time) throws Exception {
		try {
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			ResultSet rs3 = null;


			Connection con = getConnection();
			rs1 = con.createStatement().executeQuery("SELECT * FROM cinema WHERE name = '"+cinema+"'");
			rs2 = con.createStatement().executeQuery("SELECT * FROM movie WHERE name = '"+movie+"'");
	    	rs3 = con.createStatement().executeQuery("SELECT * FROM showing WHERE cinema = '"+cinema+"' AND movie='"+movie+"' AND showtime='"+time+"'");
			if((!rs1.next() || !rs2.next()) || (rs3.next())){
				return false;
			}
			String sql = "INSERT INTO showing(showtimeID, cinema, movie, showtime) VALUES (NULL,'"+cinema+"','"+movie+"','"+time+"')";
			PreparedStatement insert = con.prepareStatement(sql);
			insert.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	
	public ArrayList<String> getMovies(){
		ArrayList<String> movies_list = new ArrayList<String>();
		try {
			 ResultSet rs1 = null;

			 Connection con = getConnection();
			 rs1 = con.createStatement().executeQuery("SELECT * FROM movie");
			 while(rs1.next()){
				 movies_list.add(rs1.getString(1));
			 }	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return movies_list;
	}

	
	public ArrayList<String> getCinemas(){
		ArrayList<String> cinemas_list = new ArrayList<String>();
		try {
			 ResultSet rs1 = null;

			 Connection con = getConnection();
			 rs1 = con.createStatement().executeQuery("SELECT * FROM cinema");
			 while(rs1.next()){
				 cinemas_list.add(rs1.getString(1));
			 }	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cinemas_list;
	}

	
	/**
	 * gets the connection from mysql
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception{
		try{
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/movieapp?autoReconnect=true&useSSL=false&verifyServerCertificate=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String username = "root";
			String password = "";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url,username,password);
			//System.out.println("Connected");
			return conn;
		} catch(Exception e){System.out.println(e);}


		return null;
	}

	public String toString() {
		return userName + " " + passWord + " " + firstName + " " + lastName;
	}

	public void deleteCinema(String cinema_name) {
		try {
			String delete1 = ("DELETE FROM cinema WHERE name = '"+cinema_name+"'");
			String delete2 = ("DELETE FROM showing WHERE cinema = '"+cinema_name+"'");
			
			Connection con = getConnection();
			
			
			PreparedStatement deleteCinemas = con.prepareStatement(delete1);
			deleteCinemas.executeUpdate();
			
			PreparedStatement deleteShowtimes = con.prepareStatement(delete2);
			deleteShowtimes.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public void deleteMovie(String movie_name) {
		try {
			String delete1 = ("DELETE FROM movie WHERE name = '"+movie_name+"'");
			String delete2 = ("DELETE FROM showing WHERE movie = '"+movie_name+"'");
			
			Connection con = getConnection();
			PreparedStatement deleteMovies = con.prepareStatement(delete1);
			deleteMovies.executeUpdate();
			
			PreparedStatement deleteShowtimes = con.prepareStatement(delete2);
			deleteShowtimes.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}



}
