package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginController {
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;
    public JFXButton btnLogin;
    public JFXButton btnExit;

    public void btnLoginOnAction(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (isValidLogin(username, password, "Manager")) {
            // Manager login
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../views/manager.fxml"));
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Next Page");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (isValidLogin(username, password, "Cashier")) {
            // Cashier login
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../views/cashier.fxml"));
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Next Page");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Invalid login
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login Error");
            alert.setContentText("Please check your username and password.");
            alert.showAndWait();
        }
    }

    private boolean isValidLogin(String username, String password, String userType) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String id = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    String user = parts[3];
                    String pass = parts[4];
                    String type = parts[5];

                    if (username.equals(user) && password.equals(pass) && userType.equals(type)) {
                        return true; // Valid login
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Invalid login
    }

    public void btnExitOnAction(ActionEvent actionEvent) {
        Platform.exit();
    }
}
