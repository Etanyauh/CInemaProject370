package controller;
//import com.mysql.cj.conf.StringProperty;
//import javafx.beans.property.SimpleStringProperty;
//
public class ShowtimeDetails {
//	private final SimpleStringProperty movie_name;
//	private final SimpleStringProperty rating;
//	private final SimpleStringProperty cinema;
//	private final SimpleStringProperty rating;
//
//	
//
//	public ShowtimeDetails(String name, String rating){
//		this.name = new SimpleStringProperty(name);
//		this.rating = new SimpleStringProperty(rating);
//		
//	}
//	
//
//	
//	public String getName() {
//		return name.get();
//	}
//	
//	public String getRating() {
//		return rating.get();
//	}
//	
//
//	
//
//	public void setFrom(String val){
//		name.set(val);
//	}
//	
//	public void setRating(String val){
//		rating.set(val);
//	}
//	
//

	
	public SimpleStringProperty nameProperty(){
		return name;
	}
	
	public SimpleStringProperty ratingProperty(){
		return rating;
	}
	
}


