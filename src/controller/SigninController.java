package controller;

import javafx.event.ActionEvent;  

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import model.*;
import javafx.scene.control.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Controller class for the first vista.
 */
public class SigninController {

    boolean authenticateUser(String user, String pw) {
        boolean status = false;
        if(user != "" && pw != "") {
            status = true;
        }

        return status;
    }

    @FXML private TextField usernameField;
    @FXML private PasswordField pass_field;
    @FXML private Label status_label;
    private Database dataBase = new Database();

    

    @FXML
    void cinemaHandle(ActionEvent event) throws Exception {
    	Current.getSession().tab = "cinema";
        ViewNavigator.loadScreen(ViewNavigator.CUSTOMER_VIEW);           
    }
    
    @FXML
    void movieHandle(ActionEvent event) throws Exception {
    	Current.getSession().tab = "movie";
        ViewNavigator.loadScreen(ViewNavigator.CUSTOMER_VIEW);           
    }

    @FXML
    void adminHandle (ActionEvent event) throws NoSuchAlgorithmException {
    	if(pass_field.getText().equals("admin")){
    		ViewNavigator.loadScreen(ViewNavigator.ADMIN_VIEW);
    	} else {
    		status_label.setText("Incorrect password!");
    	}
    }
    
    
            


}