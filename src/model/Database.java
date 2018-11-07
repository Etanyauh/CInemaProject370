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
			String cinemas = "CREATE TABLE IF NOT EXISTS cinema(x int,y int,name varchar(255),PRIMARY KEY(name))";
			String movies = "CREATE TABLE IF NOT EXISTS movie(name varchar(255), rating varchar(255), PRIMARY KEY(name))";
			String showing = "CREATE TABLE IF NOT EXISTS showing(showtimeID int)";
			
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

	public void insertMovie(String name, String rating) throws Exception{

		try {
			String sql = "INSERT INTO movies (name,rating) VALUES ('"+name+"','"+rating+"')";
			Connection con = getConnection();
			PreparedStatement insert = con.prepareStatement(sql);
			insert.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void insertCinema(String name, int x, int y) throws Exception {
		try {
			String sql = "INSERT INTO cinemas(x,y,name) VALUES ('"+x+"','"+y+"', '"+name+"')";
			Connection con = getConnection();
			PreparedStatement insert = con.prepareStatement(sql);
			insert.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertShowtime(String cinema, String movie, int time) throws Exception {
		try {
			String sql = "INSERT INTO showing(cinema,movie, time) VALUES ('"+cinema+"','"+movie+"','"+time+"')";
			Connection con = getConnection();
			PreparedStatement insert = con.prepareStatement(sql);
			insert.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getCinema(String cinema) throws Exception{
		try {

			Connection con = getConnection();

			String sqlStatement = "SELECT * FROM cinemas WHERE user =" + "'" + cinema + "'"; 

			PreparedStatement statement = con.prepareStatement(sqlStatement);
			ResultSet result = statement.executeQuery();

			ArrayList<String> array = new ArrayList<String>();
			while(result.next()) {
				//System.out.print(result.getString("user") + " ");
				//System.out.println(result.getString("password"));
				array.add(result.getString("x"));
				array.add(result.getString("y"));
				array.add(result.getString("name"));
				
			}
			System.out.println("All records have been selected");
			return array;

		}catch(Exception e) {
			e.printStackTrace();
		}

		return null;
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
			String password = "ziggy9214";
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


}
