package model;

public class Current {

	private static Current session;
	public String movie;
	public String cinema;
	
	
	private Current(){
		
	}
	
	public void clearSession(){
		session.movie = "";
		session.cinema = "";
		
	}
	
	public static synchronized Current getSession() {
	      if (session == null) {
	         session = new Current();
	      }
	      return session;
	   }
}
