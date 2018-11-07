package controller;


import java.security.NoSuchAlgorithmException;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Current;
import model.Database;

public class Admin_SignIn_Controller {
	
	@FXML private Label statusLabel;
	@FXML private PasswordField password_field_1;

    String admin_password = "admin";

	
	private Database dataBase = new Database();
	
	@FXML
	void adminLogin (ActionEvent event) throws Exception {
        if(password_field_1.getText().trim().equals(admin_password)){
            ViewNavigator.loadScreen(ViewNavigator.EmailScreen);
        } else {
        	statusLabel.setText("Incorrect Passoword");
        }
    }
	
	@FXML void backHandle(ActionEvent event) { 
		ViewNavigator.loadScreen(ViewNavigator.SIGN_IN);
	}
}