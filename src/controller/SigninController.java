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
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    private Database dataBase = new Database();

    

    @FXML
    void customerHandle(ActionEvent event) throws Exception {
        ViewNavigator.loadScreen(ViewNavigator.CUSTOMER_VIEW);           
    }

    @FXML
    void adminHandle (ActionEvent event) throws NoSuchAlgorithmException {
    	ViewNavigator.loadScreen(ViewNavigator.ADMIN_SIGNIN_SCREEN);
    }
            


}