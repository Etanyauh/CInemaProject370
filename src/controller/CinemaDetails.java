package controller;
import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class CinemaDetails {
	private final SimpleStringProperty name;
	private final SimpleStringProperty x;
	private final SimpleStringProperty y;

	public CinemaDetails(String name, String x, String y){
		this.name = new SimpleStringProperty(name);
		this.x = new SimpleStringProperty(x);
		this.y = new SimpleStringProperty(y);
	}
	
	
	public String getName() {
		return name.get();
	}
	
	public String getX() {
		return x.get();
	}
	
		
	public String getY() {
		return y.get();
	}
	
	
	public SimpleStringProperty nameProperty(){
		return name;
	}
	
	public SimpleStringProperty xProperty(){
		return x;
	}
	
	public SimpleStringProperty yProperty(){
		return y;
	}
}

