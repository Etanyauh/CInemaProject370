package controller;

import com.mysql.cj.conf.StringProperty;

import javafx.beans.property.SimpleStringProperty;

public class MovieDetails {
	private final SimpleStringProperty name;
	private final SimpleStringProperty rating;

	public MovieDetails(String name, String rating){
		this.name = new SimpleStringProperty(name);
		this.rating = new SimpleStringProperty(rating);
		
	}
	

	
	public String getName() {
		return name.get();
	}
	
	public String getRating() {
		return rating.get();
	}
	

	

	public void setFrom(String val){
		name.set(val);
	}
	
	public void setRating(String val){
		rating.set(val);
	}
	


	
	public SimpleStringProperty nameProperty(){
		return name;
	}
	
	public SimpleStringProperty ratingProperty(){
		return rating;
	}
	
}

