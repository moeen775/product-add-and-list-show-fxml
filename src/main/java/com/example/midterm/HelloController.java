package com.example.midterm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelloController {
    @FXML
    private TableView<Product> productListTableView;

    @FXML
    private TextField productNameField;
    @FXML
    private TextField idField;
    @FXML
    private ComboBox<String> materialComboBox;
    @FXML
    private ComboBox<Integer> quantityComboBox;
    @FXML
    private DatePicker deliveryDatePicker;
    @FXML
    private Label errorLabel;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configure TableView columns
        TableColumn<Product, String> productNameCol = new TableColumn<>("Product Name");
        productNameCol.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());

        TableColumn<Product, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        TableColumn<Product, String> materialCol = new TableColumn<>("Material");
        materialCol.setCellValueFactory(cellData -> cellData.getValue().materialProperty());

        TableColumn<Product, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        productListTableView.getColumns().addAll(productNameCol, idCol, materialCol, quantityCol);

        // Bind TableView to productList
        productListTableView.setItems(productList);
    }

    @FXML
    public void handleAddProduct(ActionEvent event) {
        String productName = productNameField.getText();
        String id = idField.getText();
        String material = materialComboBox.getValue();
        Integer quantity = quantityComboBox.getValue();
        LocalDate deliveryDate = deliveryDatePicker.getValue(); // Get the selected date

        if (productName.isEmpty() || id.isEmpty() || deliveryDate == null) {
            errorLabel.setText("Please fill in all fields.");
            return; // Prevent adding the product if there are errors
        } else if (idExists(id)) {
            errorLabel.setText("ID already exists. Please choose a different one.");
            return; // Prevent adding the product if there are errors
        } else if (deliveryDate.isBefore(LocalDate.now())) {
            errorLabel.setText("Delivery date cannot be in the past.");
            return; // Prevent adding the product if there are errors
        }

        if (material != null && quantity != null) {

            Product product = new Product(productName, id, material, quantity, deliveryDate);
            productList.add(product);

            // Refresh the TableView to show the updated data
            productListTableView.setItems(null);
            productListTableView.setItems(productList);

            // Clear the input fields after adding the product
            productNameField.clear();
            idField.clear();
            materialComboBox.getSelectionModel().clearSelection();
            quantityComboBox.getSelectionModel().clearSelection();
            deliveryDatePicker.getEditor().clear();
        }
    }

    private boolean idExists(String id) {
        for (Product product : productList) {
            if (product.getId().equals(id)) {
                return true; // ID already exists
            }
        }
        return false; // ID does not exist
    }
}