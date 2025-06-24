package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerController {
    public ImageView btnBooks;
    public ImageView btnUser;
    public JFXButton btnBack;

    public void btnBooksonAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../views/books.fxml"));
            Stage stage = (Stage) btnBooks.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Next Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnUaseOnAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../views/user.fxml"));
            Stage stage = (Stage) btnUser.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Next Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
            Stage stage = (Stage) btnUser.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Next Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
