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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Book;
import model.User;

import java.io.*;

public class BooksController {
    public TableView tblBook;
    public TableColumn colName;
    public TableColumn colCategorie;
    public TableColumn colPrice;
    public JFXTextField txtName;
    public JFXTextField txtPrice;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnSave;
    public JFXButton btnExit;
    public JFXButton btnBack;
    public JFXComboBox comboCategorie;
    public JFXTextField txtSearchBooks;
    public JFXButton btnSearch;
    private ObservableList<Book> dataList; // Declare as a class member variable


    public void initialize() {
        comboCategorie.setItems(FXCollections.observableArrayList("Fiction","Science Fiction","Fantasy","Mystery","Thriller","Romance","Horror","Biography","Self-Help","History"));

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categories"));

        dataList = FXCollections.observableArrayList();

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

        tblBook.setItems(dataList);
    }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Book selectedBook = (Book) tblBook.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            return;
        }

        tblBook.getItems().remove(selectedBook);

        try {
            File file = new File("books.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    String price = parts[2];
                    String category = parts[3];

                    if (name.equals(selectedBook.getName())
                            && price.equals(selectedBook.getPrice())
                            && category.equals(selectedBook.getCategories())) {
                        continue;
                    }
                }

                writer.write(line);
                writer.newLine();
            }

            writer.close();
            reader.close();

            if (!file.delete()) {
                System.out.println("Failed to delete the original file.");
                return;
            }

            if (!tempFile.renameTo(file)) {
                System.out.println("Failed to rename the temporary file.");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Book selectedBook = (Book) tblBook.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            selectedBook.setName(txtName.getText());
            selectedBook.setPrice(Double.parseDouble(txtPrice.getText()));
            selectedBook.setCategories(comboCategorie.getValue().toString());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
                ObservableList<Book> bookList = tblBook.getItems();
                for (Book book : bookList) {
                    String data = book.getId() + ";" + book.getName() + ";" + book.getPrice() + ";" +
                            book.getCategories();
                    writer.write(data);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            clear();
            loadData();
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {

        Book book = new Book(txtName.getText(),Double.parseDouble(txtPrice.getText()),(String) comboCategorie.getValue());

        if (book.getName().isEmpty() || book.getPrice()==0 || book.getCategories() == null) {
            return;
        }
        String bookData = book.getId() + ";" +book.getName() + ";" + book.getPrice() + ";" + book.getCategories();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt", true));
            writer.write(bookData);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clear();
        loadData();
    }

    private void clear(){
        txtName.clear();
        txtPrice.clear();
        comboCategorie.getSelectionModel().clearSelection();
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

    public void btnSearchOnActions(ActionEvent actionEvent) {
        String searchQuery = txtSearchBooks.getText().trim().toLowerCase();
        ObservableList<Book> filteredList = FXCollections.observableArrayList();

        if (searchQuery.isEmpty()) {
            tblBook.setItems(dataList); // Display all books if the search query is empty
        } else {
            // Filter books based on name, category, or price
            for (Book book : dataList) {
                if (book.getName().toLowerCase().contains(searchQuery) ||
                        book.getCategories().toLowerCase().contains(searchQuery) ||
                        String.valueOf(book.getPrice()).contains(searchQuery)) {
                    filteredList.add(book);
                }
            }

            tblBook.setItems(filteredList);
        }
    }
}
