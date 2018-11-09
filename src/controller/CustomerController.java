	package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Current;
import model.Database;

public class CustomerController implements Initializable {

	
	@FXML private TextField rating_field;	
	@FXML private TextField x_field;
	@FXML private TextField y_field;
	@FXML private TextField radius_field;
	@FXML private TextArea showtimes_area;
	@FXML private TextArea movies_textarea;
	@FXML private TextArea cinemas_textarea;
	@FXML private Button rating_button;
	@FXML private Button address_button;
	@FXML private Label cinemas_playing_label;
	@FXML private Label movies_showing_label;
	@FXML private TableView<CinemaDetails> cinemas_table;
	@FXML private TableView<MovieDetails> movies_table;
	@FXML private TableColumn<MovieDetails,String> movie_name;
	@FXML private TableColumn<MovieDetails,String> movie_rating;
	@FXML private TableColumn<CinemaDetails,String> cinema_name;
	@FXML private TableColumn<CinemaDetails,String> cinema_x;
	@FXML private TableColumn<CinemaDetails,String> cinema_y;
	private ObservableList<MovieDetails> data;
	private ObservableList<CinemaDetails> data2;
	

    private Database dataBase = new Database();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		rating_field.setPromptText("G,PG,PG13,R,NC17");
		x_field.setPromptText("X");
		y_field.setPromptText("Y");
		radius_field.setPromptText("R");
		
		movies_table.setRowFactory(tv ->{
    		TableRow<MovieDetails> row = new TableRow<>();
    	    row.setOnMouseClicked(event -> {
    	    	if (!row.isEmpty() && event.getClickCount() >= 1) {
    	    		MovieDetails movie = row.getItem();
    	    		cinemas_playing_label.setText(movie.getName());

    	    		movies_textarea.setText(rowChosen(movie));
    	    		movies_textarea.setEditable(false);

    	    	}
    	    });
    	    return row ;
    	 });
		
		
		cinemas_table.setRowFactory(tv ->{
    		TableRow<CinemaDetails> row = new TableRow<>();
    	    row.setOnMouseClicked(event -> {
    	    	if (!row.isEmpty() && event.getClickCount() >= 1) {
    	    		CinemaDetails cinema = row.getItem();
    	    		movies_showing_label.setText(cinema.getName());

    	    		cinemas_textarea.setText(rowChosen(cinema));
    	    		cinemas_textarea.setEditable(false);

    	    	}
    	    });
    	    return row ;
    	 });


		loadTables();
	}
	
	 @FXML void searchRating(ActionEvent event){
		 String ratings = rating_field.getText();
		 String[] rateArray = ratings.split(",");
		 loadRatings(rateArray);
	    }
	 
	 @FXML void searchAddress(ActionEvent event){
		 String x = x_field.getText();
		 String y = y_field.getText();
		 String r = radius_field.getText();
		 loadLocation(x,y,r);
	    }
	 
    
	
	String rowChosen(MovieDetails m){
		MovieDetails object =  m;
		String movie_name = object.getName();
		ArrayList<String> cinema_list = new ArrayList<String>();
		StringBuilder formattedString = new StringBuilder();
    	Connection connect;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
			try {
				connect = dataBase.getConnection();
				rs = connect.createStatement().executeQuery("SELECT * FROM showing WHERE movie = '"+movie_name+"'");
				while (rs.next()) {
					cinema_list.add(rs.getString(2));
		    	}	
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	for(String cin: cinema_list){
	    		formattedString.append(cin+"\n");
	    		formattedString.append("==================\n");

	    		try {
					connect = dataBase.getConnection();
					rs2 = connect.createStatement().executeQuery("SELECT * FROM showing WHERE movie = '"+movie_name+"'");
					while (rs2.next()) {
						if(rs2.getString(2).equals(cin)){
							formattedString.append(rs2.getString(4) + "\n");
						}
			    	}	
		    		formattedString.append("\n");

				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	
	    return formattedString.toString();
	    		
    }
	
	String rowChosen(CinemaDetails m){
		CinemaDetails object =  m;
		String cinema_name = object.getName();
		ArrayList<String> movie_list = new ArrayList<String>();
		StringBuilder formattedString = new StringBuilder();
    	Connection connect;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
			try {
				connect = dataBase.getConnection();
				rs = connect.createStatement().executeQuery("SELECT * FROM showing WHERE cinema = '"+cinema_name+"'");
				while (rs.next()) {
					movie_list.add(rs.getString(3));
		    	}	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	    	for(String movie: movie_list){
	    		formattedString.append(movie+"\n");
	    		formattedString.append("=================\n");

	    		try {
					connect = dataBase.getConnection();
					rs2 = connect.createStatement().executeQuery("SELECT * FROM showing WHERE cinema = '"+cinema_name+"'");
					while (rs2.next()) {
						if(rs2.getString(3).equals(movie)){
							formattedString.append(rs2.getString(4) + "\n");
						}
			    	}
		    		formattedString.append("\n");

				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	
	    return formattedString.toString();
	    		
    }
    
	
    public void loadTables(){

		data = FXCollections.observableArrayList();
		ObservableList<MovieDetails> dataTemp = FXCollections.observableArrayList();
		Connection connect;
		try {
			connect = dataBase.getConnection();
	    	ResultSet rs = connect.createStatement().executeQuery("SELECT * FROM movie");
	    	while (rs.next()) {
	    		data.add(new MovieDetails(rs.getString(1),rs.getString(2)));
	    	}	
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		movie_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		movie_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
    	movies_table.setItems(data);
	
 
    
    data2 = FXCollections.observableArrayList();
	try {
		connect = dataBase.getConnection();
    	ResultSet rs = connect.createStatement().executeQuery("SELECT * FROM cinema");
    	while (rs.next()) {
    		data2.add(new CinemaDetails(rs.getString(1),rs.getString(2),rs.getString(3)));
    	}	
    	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	cinema_name.setCellValueFactory(new PropertyValueFactory<>("name"));
	cinema_x.setCellValueFactory(new PropertyValueFactory<>("x"));
	cinema_y.setCellValueFactory(new PropertyValueFactory<>("y"));

	cinemas_table.setItems(data2);
	
    }

	
    
    
    // Ratings and Address Search
    
    public void loadRatings(String[] arr){
    	data = FXCollections.observableArrayList();
		ObservableList<MovieDetails> dataTemp = FXCollections.observableArrayList();
		Connection connect;
		for(String rating: arr){
			try {
				connect = dataBase.getConnection();
		    	ResultSet rs = connect.createStatement().executeQuery("SELECT * FROM movie WHERE rating = '"+rating+"'");
		    	while (rs.next()) {
		    		data.add(new MovieDetails(rs.getString(1),rs.getString(2)));
		    	}	
		    	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		movie_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		movie_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
    	movies_table.setItems(data);
    }
    
    
    
    
    public void loadLocation(String x, String y, String r){
    	int x_search = 0;
    	int y_search = 0;
    	int r_search = 0;
    	
    	try {
	    	x_search = Integer.parseInt(x);
	    	y_search = Integer.parseInt(y);
	    	r_search = Integer.parseInt(r);
    	} catch(NumberFormatException ex){
    		System.out.println("Not valid numbers");
    	}
    	
    	
    	data2 = FXCollections.observableArrayList();
		ObservableList<MovieDetails> dataTemp = FXCollections.observableArrayList();
		Connection connect;
			try {
				connect = dataBase.getConnection();
		    	ResultSet rs = connect.createStatement().executeQuery("SELECT * FROM cinema");
		    	while (rs.next()) {
		    		int x_cinema = Integer.parseInt(rs.getString(2));
		    		int y_cinema = Integer.parseInt(rs.getString(2));
		    		
		    		int distance = (int) Math.sqrt(Math.pow((x_cinema-x_search), 2) + Math.pow(y_cinema-y_search, 2));
		    		
		    		if(distance < r_search){
		    			data2.add(new CinemaDetails(rs.getString(1),rs.getString(2),rs.getString(3)));
		    		}
		    	}	
		    	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cinema_name.setCellValueFactory(new PropertyValueFactory<>("name"));
			cinema_x.setCellValueFactory(new PropertyValueFactory<>("x"));
			cinema_y.setCellValueFactory(new PropertyValueFactory<>("y"));

			cinemas_table.setItems(data2);
		}
	   
    }
	
	
