package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Book;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CashierController {
    public JFXButton btnExit;
    public JFXButton btnBack;
    public TableColumn colName;
    public TableColumn colCatagory;
    public TableColumn colPrice;
    public JFXTextField txtSearch;
    public JFXButton btnSearch;
    public TableView tblBooks;

    private ObservableList<Book> dataList; // Declare as a class member variable

    public void btnExitOnAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCatagory.setCellValueFactory(new PropertyValueFactory<>("categories"));

        loadData();
    }

    public void loadData() {
        dataList = FXCollections.observableArrayList(); // Initialize the dataList

        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    String category = parts[3];

                    Book book = new Book(name, price, category);
                    dataList.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tblBooks.setItems(dataList);
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String searchQuery = txtSearch.getText().trim().toLowerCase();
        ObservableList<Book> filteredList = FXCollections.observableArrayList();

        if (searchQuery.isEmpty()) {
            tblBooks.setItems(dataList); // Display all books if the search query is empty
        } else {
            // Filter books based on name, category, or price
            for (Book book : dataList) {
                if (book.getName().toLowerCase().contains(searchQuery) ||
                        book.getCategories().toLowerCase().contains(searchQuery) ||
                        String.valueOf(book.getPrice()).contains(searchQuery)) {
                    filteredList.add(book);
                }
            }

            tblBooks.setItems(filteredList);
        }
    }
}
