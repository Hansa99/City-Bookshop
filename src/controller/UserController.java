package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.User;

import java.io.*;

public class UserController {
    public JFXButton btnExit;
    public JFXButton btnBack;
    public TableView tblMainTable;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colUserType;
    public TableColumn colUsername;
    public TableColumn colPassword;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtUsername;
    public JFXTextField txtPassword;
    public JFXComboBox comboType;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;


    public void initialize() {
        comboType.setItems(FXCollections.observableArrayList("Cashier", "Manager"));

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUserType.setCellValueFactory(new PropertyValueFactory<>("userType"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        loadAllData();
    }

    public void btnExitOnAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../views/manager.fxml"));
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Next Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAllData() {
        ObservableList<User> dataList = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String id = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    String username = parts[3];
                    String password = parts[4];
                    String userType = parts[5];

                    User data = new User( name, email, username, password, userType);
                    dataList.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tblMainTable.setItems(dataList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        User user = new User(txtName.getText(),txtEmail.getText(),txtUsername.getText(),txtPassword.getText(),comboType.getValue().toString());
        String data = user.getId() + "," +user.getName() + "," + user.getEmail() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getUserType();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt", true));
            writer.write(data);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clear();
        loadAllData();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        User selectedUser = (User) tblMainTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            selectedUser.setName(txtName.getText());
            selectedUser.setEmail(txtEmail.getText());
            selectedUser.setUsername(txtUsername.getText());
            selectedUser.setPassword(txtPassword.getText());
            selectedUser.setUserType(comboType.getValue().toString());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt"))) {
                ObservableList<User> userList = tblMainTable.getItems();
                for (User user : userList) {
                    String data = user.getId() + "," + user.getName() + "," + user.getEmail() + "," +
                            user.getUsername() + "," + user.getPassword() + "," + user.getUserType();
                    writer.write(data);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            clear();
            loadAllData();
        }
    }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        User selectedUser = (User) tblMainTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            tblMainTable.getItems().remove(selectedUser);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt"))) {
                ObservableList<User> userList = tblMainTable.getItems();
                for (User user : userList) {
                    String data = user.getId() + "," + user.getName() + "," + user.getEmail() + "," +
                            user.getUsername() + "," + user.getPassword() + "," + user.getUserType();
                    writer.write(data);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void clear(){
        txtName.clear();
        txtEmail.clear();
        txtUsername.clear();
        txtPassword.clear();
        comboType.getSelectionModel().clearSelection();
    }
}
