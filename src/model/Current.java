package model;

public class Current {

	private static Current session;
	public String movie;
	public String cinema;
	public String tab;
	public boolean admin_bool = false;
	
	
	private Current(){
		
	}
	
	public void clearSession(){
		session.movie = "";
		session.cinema = "";
		session.tab ="";
	}
	
	public static synchronized Current getSession() {
	      if (session == null) {
	         session = new Current();
	      }
	      return session;
	   }
}
