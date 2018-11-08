	package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	@FXML private Button rating_button;
	@FXML private Button address_button;
	@FXML private TableView<CinemaDetails> cinemas_table;
	@FXML private TableView<MovieDetails> movies_table;
	@FXML private TableColumn<MovieDetails,String> movie_name;
	@FXML private TableColumn<MovieDetails,String> movie_rating;
	@FXML private TableColumn<CinemaDetails,String> cinema_name;
	@FXML private TableColumn<CinemaDetails,String> cinema_x;
	@FXML private TableColumn<CinemaDetails,String> cinema_y;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		rating_field.setPromptText("G,PG,PG13,R,NC17");
		x_field.setPromptText("X");
		y_field.setPromptText("Y");



	}

    
}
