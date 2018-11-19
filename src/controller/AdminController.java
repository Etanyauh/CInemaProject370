	package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Current;
import model.Database;

public class AdminController implements Initializable {

	
	@FXML private TextField add_name_field;
	@FXML private ComboBox add_rating_field;
	@FXML private TextField add_cinema_name_field;
	@FXML private TextField add_x_field;
	@FXML private TextField add_y_field;
	@FXML private ComboBox add_showtime_cinema_field;
	@FXML private ComboBox add_showtime_movie_field;
	@FXML private ComboBox add_showtime_time_field;
	@FXML private Label status_label;
	@FXML private Label status_label_movie;


	
	
	
	@FXML private TextArea showtimes_area;
	@FXML private TextArea movies_textarea;
	@FXML private TextArea cinemas_textarea;
	@FXML private Button delete_movie;
	@FXML private Button delete_cinema;
	@FXML private TableView<CinemaDetails> cinemas_table;
	@FXML private TableView<MovieDetails> movies_table;
	@FXML private TableColumn<MovieDetails,String> movie_name;
	@FXML private TableColumn<MovieDetails,String> movie_rating;
	@FXML private TableColumn<CinemaDetails,String> cinema_name;
	@FXML private TableColumn<CinemaDetails,String> cinema_x;
	@FXML private TableColumn<CinemaDetails,String> cinema_y;
	private ObservableList<MovieDetails> data;
	private ObservableList<CinemaDetails> data2;
	@FXML private Tab customer_tab;
	@FXML private Tab movie_tab;
	@FXML private Tab cinema_tab;

    private Database dataBase = new Database();
    
    
    void updateCombos(){
    	List<String> list_movies = dataBase.getMovies() ;
		List<String> list_cinemas = dataBase.getCinemas();
		ObservableList<String> movieList = FXCollections.observableList(list_movies);
        ObservableList<String> cinemaList = FXCollections.observableList(list_cinemas);
        add_showtime_movie_field.getItems().clear();
        add_showtime_movie_field.setItems(movieList);
        add_showtime_cinema_field.getItems().clear();
        add_showtime_cinema_field.setItems(cinemaList);
		
    }

    @FXML void goCustomerButton(ActionEvent event){
    	ViewNavigator.loadScreen(ViewNavigator.CUSTOMER_VIEW);
    }
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		List<String> list_movies = dataBase.getMovies() ;
		List<String> list_cinemas = dataBase.getCinemas();
		
		
		List<String> listRating = new ArrayList<String>();
        listRating.add("G");
        listRating.add("PG");
        listRating.add("PG13");
        listRating.add("R");
        listRating.add("NC17");
	
        
		List<String> list = new ArrayList<String>();
        list.add("1:30 pm");
        list.add("4:00 pm");
        list.add("5:00 pm");
        list.add("7:00 pm");
        list.add("8:00 pm");
        list.add("9:00 pm");
        list.add("12:00 am");
        
        ObservableList<String> obList = FXCollections.observableList(list);
        ObservableList<String> rateList = FXCollections.observableList(listRating);
        ObservableList<String> movieList = FXCollections.observableList(list_movies);
        ObservableList<String> cinemaList = FXCollections.observableList(list_cinemas);


        add_showtime_time_field.getItems().clear();
        add_showtime_time_field.setItems(obList);
        
        add_rating_field.getItems().clear();
        add_rating_field.setItems(rateList);
        
        add_showtime_movie_field.getItems().clear();
        add_showtime_movie_field.setItems(movieList);
        add_showtime_cinema_field.getItems().clear();
        add_showtime_cinema_field.setItems(cinemaList);
		
        
        customer_tab.setOnSelectionChanged(event -> {
            if (customer_tab.isSelected()) {
                ViewNavigator.loadScreen(ViewNavigator.CUSTOMER_VIEW);
                //Do stuff here
            }
        });
        
        movie_tab.setOnSelectionChanged(event -> {
            if (movie_tab.isSelected()) {
            	status_label.setText("");;
            }
        });
        
        cinema_tab.setOnSelectionChanged(event -> {
            if (cinema_tab.isSelected()) {
            	status_label_movie.setText("");;
            }
        });
        
        
        
        
        
		movies_table.setRowFactory(tv ->{
    		TableRow<MovieDetails> row = new TableRow<>();
    	    row.setOnMouseClicked(event -> {
    	    	if (!row.isEmpty() && event.getClickCount() >= 1) {
    	    		MovieDetails movie = row.getItem();
    	    		movies_textarea.setText(rowChosen(movie));
    	    		movies_textarea.setEditable(false);
    	    		Current.getSession().movie = movie.getName();

    	    	}
    	    });
    	    return row ;
    	 });
		
		
		cinemas_table.setRowFactory(tv ->{
    		TableRow<CinemaDetails> row = new TableRow<>();
    	    row.setOnMouseClicked(event -> {
    	    	if (!row.isEmpty() && event.getClickCount() >= 1) {
    	    		CinemaDetails cinema = row.getItem();
    	    		cinemas_textarea.setText(rowChosen(cinema));
    	    		cinemas_textarea.setEditable(false);
    	    		Current.getSession().cinema = cinema.getName();
    	    	}
    	    });
    	    return row ;
    	 });


		loadTables();
	}
	
	 @FXML void deleteMovie(ActionEvent event){
		 String movie = Current.getSession().movie;
	 	if(!movie.equals("") && !(movie==null)){
	 		dataBase.deleteMovie(movie);
	 	}
	 	cinemas_textarea.clear();
	 	movies_textarea.clear();
 		Current.getSession().cinema = "";
	 	loadTables();
	 	updateCombos();
	 }
	 
	 @FXML void deleteCinema(ActionEvent event){
		 String cinema = Current.getSession().cinema;
		 if(!cinema.equals("") && !(cinema==null)){
			dataBase.deleteCinema(cinema);
		 }
		 cinemas_textarea.clear();
		 movies_textarea.clear();
		 Current.getSession().cinema = "";
		 loadTables();
	 	updateCombos();

    }
	 
	 @FXML void addMovie(ActionEvent event){
		String movie_name = add_name_field.getText();
		String movie_rating = add_rating_field.getSelectionModel().getSelectedItem().toString();
		add_name_field.clear();
		add_rating_field.getSelectionModel().clearSelection();
		try {
			boolean res = dataBase.insertMovie(movie_name, movie_rating);
			if(res == false) {
				 status_label_movie.setText("Error inserting movie. Movie already exists!");

			 } else {
				 status_label_movie.setText("");
				 add_name_field.clear();

			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadTables();
	 	updateCombos();

	 }
	 
	 @FXML void addCinema(ActionEvent event){
		 String cinema_name = add_cinema_name_field.getText();
		 String x = add_x_field.getText();
		 String y = add_y_field.getText();
		 if(Integer.parseInt(x) > 100 || Integer.parseInt(y)  > 100){
				status_label.setText("Cinema address out of bounds");
				return;
		 }

		 ResultSet rs = null;		 
    	 Connection con;
		 
		 
		 try {
			 con = dataBase.getConnection();
			 rs = con.createStatement().executeQuery("SELECT * FROM cinema WHERE x='"+x+"' AND y='"+y+"'");
			if(rs.next()){
				status_label.setText("A cinema already exists at this location!");
				 add_x_field.clear();
				 add_y_field.clear();
				return;
			}
			boolean res = dataBase.insertCinema(cinema_name, x, y);
			if(res == false) {
				 status_label.setText("Error inserting cinema. Cinema already exists!");
				 add_cinema_name_field.clear();

			 } else {
				 add_cinema_name_field.clear();
				 add_x_field.clear();
				 add_y_field.clear();
				 status_label.setText("");
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			loadTables();
		 	updateCombos();

	 }
	 
	 @FXML void addShowtime(ActionEvent event){
		 String cinema =  add_showtime_cinema_field.getSelectionModel().getSelectedItem().toString();
		 String movie = add_showtime_movie_field.getSelectionModel().getSelectedItem().toString();
		 String time = add_showtime_time_field.getSelectionModel().getSelectedItem().toString();
//		 add_showtime_cinema_field.getSelectionModel().clearSelection();
//		 add_showtime_movie_field.getSelectionModel().clearSelection();
		 add_showtime_time_field.getSelectionModel().clearSelection();
		 ResultSet rs = null;
		 ResultSet rs2 = null;

		 
    	 Connection con;

		 try {
				con = dataBase.getConnection();
				rs = con.createStatement().executeQuery("SELECT * FROM showing WHERE movie='"+movie+"' AND showtime='"+time+"'");
				if(rs.next()){
					status_label.setText("Movie is already showing at selected time");
					return;
				}
				rs2 = con.createStatement().executeQuery("SELECT * FROM showing WHERE cinema='"+cinema+"' AND movie='"+movie+"'");
				int counter = 0;
				while(rs2.next()){
					counter++;
					if(counter > 3){
						status_label.setText("Movie is already showing three times at: "+cinema);
						return;
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}


		 try {
			 boolean res = dataBase.insertShowtime(cinema, movie, time);
			 if(res == false){
				 status_label.setText("Error inserting showtime. Check that fields exist!");
			 } else {
				 status_label.setText("");
			 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			loadTables();
			
		 }
	 
    
	
	String rowChosen(MovieDetails m){
		MovieDetails object =  m;
		String movie_name = object.getName();
		ArrayList<String> cinema_list = new ArrayList<String>();
		ArrayList<String> address_list = new ArrayList<String>();

		StringBuilder formattedString = new StringBuilder();
    	Connection connect;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;

		
			try {
				connect = dataBase.getConnection();
				rs = connect.createStatement().executeQuery("SELECT * FROM showing WHERE movie = '"+movie_name+"' ORDER BY cinema");
				while (rs.next()) {
					if(!cinema_list.contains(rs.getString(2)))
					cinema_list.add(rs.getString(2));
		    	}	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for(String cinema: cinema_list){
	    		
	    		try {
					connect = dataBase.getConnection();
					rs3 = connect.createStatement().executeQuery("SELECT * FROM cinema WHERE name = '"+cinema+"'");
					while (rs3.next()) {
						address_list.add(("("+rs3.getString(2)+", "+rs3.getString(3)+")"));
						}
			    	}	
					 catch (Exception e) {
					e.printStackTrace();
				}
	    		
	    	}
			
			
			
			
	    	for(int k = 0; k < cinema_list.size();k++){
	    		ArrayList<String> showtime_list = new ArrayList<String>();
	    		formattedString.append(cinema_list.get(k)+"    "+address_list.get(k)+"\n");
	    		formattedString.append("====================\n");
	    		try {
					connect = dataBase.getConnection();
					rs2 = connect.createStatement().executeQuery("SELECT * FROM showing WHERE movie = '"+movie_name+"'");
					while (rs2.next()) {
						if(rs2.getString(2).equals(cinema_list.get(k))){
							showtime_list.add(rs2.getString(4));
						}
			    	}	

				} catch (Exception e) {
					e.printStackTrace();
				}
	    		showtime_list = sortTimes(showtime_list);
	    		for(int i = 0; i < showtime_list.size(); i++){
	    			if(i == showtime_list.size() - 1){
		    			formattedString.append(showtime_list.get(i));
	    			} else {
	    			formattedString.append(showtime_list.get(i)+ ", ");
	    			}
	    		}
	    		formattedString.append("\n\n");
	    	}
	    	
	    return formattedString.toString();
	    		
    }
	

	String rowChosen(CinemaDetails m){
		CinemaDetails object =  m;
		String cinema_name = object.getName();
		ArrayList<String> movie_list = new ArrayList<String>();
		ArrayList<String> rating_list = new ArrayList<String>();
		StringBuilder formattedString = new StringBuilder();
    	Connection connect;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;

		
			try {
				connect = dataBase.getConnection();
				rs = connect.createStatement().executeQuery("SELECT * FROM showing WHERE cinema = '"+cinema_name+"'ORDER BY movie");
				while (rs.next()) {
					if(!movie_list.contains(rs.getString(3))){
					movie_list.add(rs.getString(3));
					}
		    	}	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	    	for(String movie: movie_list){
	    		
	    		try {
					connect = dataBase.getConnection();
					rs3 = connect.createStatement().executeQuery("SELECT * FROM movie WHERE name = '"+movie+"'");
					while (rs3.next()) {
						if(rs3.getString(2) == null){
							rating_list.add(("NR"));

						} else {
						rating_list.add((rs3.getString(2)));
						}
						}
			    	}	
					 catch (Exception e) {
					e.printStackTrace();
				}
	    		
	    	}
	    		
	    		for (int t = 0; t < movie_list.size(); t++){
	    		
	    		
	    		
	    		ArrayList<String> movie_sort = new ArrayList<String>();
	    		formattedString.append(movie_list.get(t)+"    "+rating_list.get(t)+"\n");
	    		formattedString.append("====================\n");	    		
	    		try {
					connect = dataBase.getConnection();
					rs2 = connect.createStatement().executeQuery("SELECT * FROM showing WHERE cinema = '"+cinema_name+"'");
					while (rs2.next()) {
						if(rs2.getString(3).equals(movie_list.get(t))){
							movie_sort.add(rs2.getString(4));

						}
			    	}	

				} catch (Exception e) {
					e.printStackTrace();
				}
	    		movie_sort = sortTimes(movie_sort);
	    		for(int i = 0; i < movie_sort.size(); i++){
	    			if(i == movie_sort.size() - 1){
		    			formattedString.append(movie_sort.get(i));
	    			} else {
	    			formattedString.append(movie_sort.get(i)+ ", ");
	    			}
	    		}
	    		formattedString.append("\n\n");

	    	}
	    	
	    return formattedString.toString();
	    		
    }
	
//	String rowChosen(CinemaDetails m){
//		CinemaDetails object =  m;
//		String cinema_name = object.getName();
//		ArrayList<String> movie_list = new ArrayList<String>();
//		StringBuilder formattedString = new StringBuilder();
//    	Connection connect;
//		ResultSet rs = null;
//		ResultSet rs2 = null;
//		
//			try {
//				connect = dataBase.getConnection();
//				rs = connect.createStatement().executeQuery("SELECT * FROM showing WHERE cinema = '"+cinema_name+"' ORDER BY movie");
//				while (rs.next()) {
//					if(!movie_list.contains(rs.getString(3))){
//					movie_list.add(rs.getString(3));
//					}
//		    	}	
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//	    	for(String movie: movie_list){
//	    		ArrayList<String> movie_sort = new ArrayList<String>();
//	    		formattedString.append(movie+"\n");
//	    		formattedString.append("====================\n");	    		
//	    		try {
//					connect = dataBase.getConnection();
//					rs2 = connect.createStatement().executeQuery("SELECT * FROM showing WHERE cinema = '"+cinema_name+"'");
//					while (rs2.next()) {
//						if(rs2.getString(3).equals(movie)){
//							movie_sort.add(rs2.getString(4));
//
//						}
//			    	}	
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//	    		movie_sort = sortTimes(movie_sort);
//	    		for(int i = 0; i < movie_sort.size(); i++){
//	    			if(i == movie_sort.size() - 1){
//		    			formattedString.append(movie_sort.get(i));
//	    			} else {
//	    			formattedString.append(movie_sort.get(i)+ ", ");
//	    			}
//	    		}
//	    		formattedString.append("\n\n");
//
//
//	    	}
//	    	
//	    return formattedString.toString();
//	    		
//    }
    
	
	private ArrayList<String> sortTimes(ArrayList<String> showtime_list) {
		Collections.sort(showtime_list, new Comparator<String>() {
	        DateFormat f = new SimpleDateFormat("hh:mm a");
	        @Override
	        public int compare(String o1, String o2) {
	            try {
	                return f.parse(o1).compareTo(f.parse(o2));
	            } catch (ParseException e) {
	                throw new IllegalArgumentException(e);
	            }
	        }
		});
		return showtime_list;
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
	
	
